package com.pb.kemchoaudio.ui.list_screen

sealed interface ListScreenAction {
    data object OnMoodChipClick: ListScreenAction
    data object OnTopicChipClick: ListScreenAction
    data object OnFabClick: ListScreenAction
    data object OnFabLongClick: ListScreenAction
    data object OnSettingsClick: ListScreenAction
    data class OnRemoveFilters(val filterType: AudioFilterType): ListScreenAction
}