package com.pb.audia.memo.presentation.utils

import com.pb.audia.app.navigation.NavigationRoute
import com.pb.audia.memo.domain.recording.RecordingDetails
import kotlin.time.Duration.Companion.milliseconds

fun RecordingDetails.toCreateMemoRoute(): NavigationRoute.CreateMemo {
    return NavigationRoute.CreateMemo(
        recordingPath = this.filePath
            ?: throw IllegalArgumentException("RecordingDetails.filePath is null"),
        duration = this.duration.inWholeMilliseconds,
        amplitudes = this.amplitudes.joinToString { ";" }
    )
}

fun NavigationRoute.CreateMemo.toRecordingDetails(): RecordingDetails {
    return RecordingDetails(
        duration = this.duration.milliseconds,
        amplitudes = this.amplitudes.split(";").map { it.toFloat() },
        filePath = this.recordingPath
    )
}