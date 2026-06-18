@file:OptIn(ExperimentalCoroutinesApi::class)

package com.pb.audia.memo.data.recording

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.pb.audia.memo.domain.recording.RecordingDetails
import com.pb.audia.memo.domain.recording.VoiceRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds

class AndroidVoiceRecorder(
    private val context: Context, private val applicationScope: CoroutineScope
) : VoiceRecorder {

    companion object {
        const val MAX_AMPLITUDE_VALUE = 26_000
    }

    private val dispatcher = Dispatchers.Default.limitedParallelism(1)

    private val _recordingDetails = MutableStateFlow(RecordingDetails())
    override val recordingDetails: StateFlow<RecordingDetails>
        get() = _recordingDetails.asStateFlow()

    private var recorder: MediaRecorder? = null
    private var isRecording = false
    private var isPaused = false
    private val amplitudes = mutableListOf<Float>()
    private val tempFile = generateTempFile()

    private var durationJob: Job? = null
    private var amplitudeJob: Job? = null

    override fun startRecording() {
        if (isRecording) {
            return
        }

        try {
            resetSession()
            val recorder = newMediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128 * 1000)
                setAudioSamplingRate(44100)
                setOutputFile(tempFile.absolutePath)

                prepare()
                start()
            }
            isRecording = true
            isPaused = false

            startTrackingRecording()
            startTrackingAmplitudes()
        } catch (e: IOException) {
            Timber.e(e, "Error starting recording")
            recorder?.release()
            recorder = null
        }
    }

    override fun pauseRecording() {
        if (!isRecording || isPaused) {
            return
        }
        recorder?.pause()
        durationJob?.cancel()
        amplitudeJob?.cancel()
    }

    override fun resumeRecording() {
        if (!isRecording || !isPaused) {
            return
        }
        recorder?.resume()
        isPaused = false
        startTrackingRecording()
        startTrackingAmplitudes()
    }

    override fun stopRecording() {
        try {
            recorder?.stop()
            recorder?.release()
        } catch (e: Exception) {
            Timber.e(e, "Failed to stop recording.")
        } finally {
            _recordingDetails.update {
                it.copy(
                    amplitudes = amplitudes.toList(),
                    filePath = tempFile.absolutePath
                )
            }
            cleanup()
        }
    }

    override fun cancelRecording() {
        stopRecording()
        resetSession()
    }

    private fun startTrackingAmplitudes() {
        amplitudeJob = applicationScope.launch {
            while (isRecording) {
                val amplitude = getAmplitude()
                withContext(dispatcher) {
                    amplitudes.add(amplitude)
                }
                delay(100L)
            }
        }
    }

    private fun getAmplitude(): Float {
        return if (isRecording) {
            try {
                val maxAmplitude = recorder?.maxAmplitude
                val amplitudeRatio = maxAmplitude?.takeIf { it > 0 }.run {
                    (this?.div(MAX_AMPLITUDE_VALUE.toFloat()))?.coerceIn(0f, 1f)
                }
                amplitudeRatio ?: 0f
            } catch (e: Exception) {
                Timber.e(e, "Failed to get amplitude.")
                0f
            }
        } else 0f
    }

    private fun startTrackingRecording() {
        durationJob = applicationScope.launch {
            var lastTime = System.currentTimeMillis()
            while (isRecording && !isPaused) {
                val currentTime = System.currentTimeMillis()
                val deltaTime = currentTime - lastTime

                _recordingDetails.update {
                    it.copy(
                        duration = it.duration + deltaTime.milliseconds
                    )
                }
                lastTime = System.currentTimeMillis()
                delay(10L)
            }
        }
    }

    private fun resetSession() {
        _recordingDetails.update { RecordingDetails() }
        applicationScope.launch(dispatcher) {
            amplitudes.clear()
            cleanup()
        }

    }

    private fun cleanup() {
        Timber.d("Cleaning up recorder resources")
        recorder = null
        isRecording = false
        isPaused = false
        durationJob?.cancel()
        amplitudeJob?.cancel()
    }

    private fun generateTempFile(): File {
        val id = UUID.randomUUID().toString()
        return File(
            context.cacheDir, "recording_$id.mp4 "
        )
    }

    private fun newMediaRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION") MediaRecorder()
        }
    }
}