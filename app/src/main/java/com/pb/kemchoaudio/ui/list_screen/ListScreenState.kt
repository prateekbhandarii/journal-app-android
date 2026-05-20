package com.pb.kemchoaudio.ui.list_screen

import android.adservices.topics.Topic
import com.pb.kemchoaudio.core.presentation.designsystem.dropdowns.Selectable
import com.pb.kemchoaudio.ui.models.MoodUi

data class ListScreenState(
    val hasAudioRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilter: Boolean = false,
    val isLoadingData: Boolean = false,
    val moods: List<Selectable<MoodUi>> = emptyList(),
    val topics: List<Selectable<Topic>> = emptyList()
)