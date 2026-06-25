package com.pb.audia.memo.presentation.list_screen

import com.pb.audia.memo.presentation.models.MoodUi
import com.pb.audia.memo.presentation.models.TrackSizeInfo

sealed interface MemoListScreenAction {
    data object OnMoodChipClick : MemoListScreenAction
    data object OnDismissMoodChipDropdown : MemoListScreenAction
    data class OnFilterByMoodClick(val moodUi: MoodUi) : MemoListScreenAction
    data object OnTopicChipClick : MemoListScreenAction
    data object OnDismissTopicChipDropdown : MemoListScreenAction
    data class OnFilterByTopicClick(val topic: String) : MemoListScreenAction
    data object OnFabClick : MemoListScreenAction
    data object OnFabLongClick : MemoListScreenAction
    data object OnSettingsClick : MemoListScreenAction
    data class OnRemoveFilters(val filterType: MemoFilterType) : MemoListScreenAction
    data class OnMemoPlayClick(val memo: Int) : MemoListScreenAction
    data class OnMemoPauseClick(val memo: Int) : MemoListScreenAction
    data class OnTrackSizeAvailable(val trackSize: TrackSizeInfo) : MemoListScreenAction
    data object OnAudioPermissionGranted : MemoListScreenAction
    data object OnCancelRecording : MemoListScreenAction
    data object OnPauseRecording : MemoListScreenAction
    data object OnResumeRecording : MemoListScreenAction
    data object OnCompleteRecording : MemoListScreenAction
}