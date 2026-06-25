package com.pb.audia.memo.presentation

interface MemoEvents {
    data object RequestAudioPermission: MemoEvents
    data object ShowShortRecordingToast: MemoEvents
    data object OnDoneRecording: MemoEvents
}