package com.pb.kemchoaudio.ui.list_screen

data class ListScreenState(
    val hasAudioRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilter: Boolean = false,
    val isLoadingData: Boolean = false
)