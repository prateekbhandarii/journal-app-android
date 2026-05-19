package com.pb.kemchoaudio.core.database.audio

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AudioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val mood: Any,
    val recordedAt: Long,
    val note: String?,
    val audioFilePath: String,
    val audioPlaybackLength: Long,
    val audioAmplitudes: List<Float>
)

