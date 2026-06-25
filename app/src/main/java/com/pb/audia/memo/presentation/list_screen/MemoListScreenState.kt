package com.pb.audia.memo.presentation.list_screen

import com.pb.audia.R
import com.pb.audia.core.presentation.designsystem.dropdowns.Selectable
import com.pb.audia.core.presentation.designsystem.dropdowns.Selectable.Companion.asUnselectedItems
import com.pb.audia.core.presentation.util.UiText
import com.pb.audia.memo.presentation.models.AudioCaptureMethod
import com.pb.audia.memo.presentation.models.MemoSection
import com.pb.audia.memo.presentation.models.MemoUi
import com.pb.audia.memo.presentation.models.MoodChipContent
import com.pb.audia.memo.presentation.models.MoodUi
import com.pb.audia.memo.presentation.models.RecordingState
import java.util.Locale
import kotlin.math.roundToInt
import kotlin.time.Duration

data class MemoListScreenState(
    val memoMap: Map<UiText, List<MemoUi>> = emptyMap(),
    val currentAudioCaptureMethod: AudioCaptureMethod? = null,
    val recordingElapsedDuration: Duration = Duration.ZERO,
    val hasAudioRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilter: Boolean = false,
    val isLoadingData: Boolean = false,
    val recordingState: RecordingState = RecordingState.NOT_RECORDING,
    val moods: List<Selectable<MoodUi>> = emptyList(),
    val topics: List<Selectable<String>> = listOf("Love", "Happy", "Work").asUnselectedItems(),
    val moodChipContent: MoodChipContent = MoodChipContent(),
    val selectedFilterChip: MemoFilterType? = null,
    val topicChipTitle: UiText = UiText.StringResource(R.string.all_topics)
) {
    val memoList = memoMap
        .toList()
        .map { (dayText, memo) ->
            MemoSection(
                dateHeader = dayText,
                memos = memo
            )
        }

    val formattedRecordDuration: String
        get() {
            val minutes = (recordingElapsedDuration.inWholeMinutes % 60).toInt()
            val seconds = (recordingElapsedDuration.inWholeSeconds % 60).toInt()
            val centiSeconds =
                ((recordingElapsedDuration.inWholeMilliseconds % 1000) / 10.0).roundToInt()

            return String.format(
                locale = Locale.US,
                format = "%02d:%02d:%02d",
                minutes,
                seconds,
                centiSeconds
            )
        }
}