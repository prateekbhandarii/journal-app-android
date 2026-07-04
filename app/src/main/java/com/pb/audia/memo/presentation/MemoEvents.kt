package com.pb.audia.memo.presentation

import com.pb.audia.memo.domain.recording.RecordingDetails

interface MemoEvents {
    data object RequestAudioPermission: MemoEvents
    data object ShowShortRecordingToast: MemoEvents
    data class OnDoneRecording(val details: RecordingDetails): MemoEvents
}