package com.pb.kemchoaudio.sonic.list_screen

import com.pb.kemchoaudio.sonic.list_screen.models.MoodUi

sealed interface ListScreenAction {
    data object OnMoodChipClick : ListScreenAction
    data object OnDismissMoodChipDropdown : ListScreenAction
    data class OnFilterByMoodClick(val moodUi: MoodUi) : ListScreenAction
    data object OnTopicChipClick : ListScreenAction
    data object OnDismissTopicChipDropdown : ListScreenAction
    data class OnFilterByTopicClick(val topic: String) : ListScreenAction
    data object OnFabClick : ListScreenAction
    data object OnFabLongClick : ListScreenAction
    data object OnSettingsClick : ListScreenAction
    data class OnRemoveFilters(val filterType: SonicFilterType) : ListScreenAction
}