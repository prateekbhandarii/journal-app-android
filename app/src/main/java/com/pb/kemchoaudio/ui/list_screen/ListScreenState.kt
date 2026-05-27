package com.pb.kemchoaudio.ui.list_screen

import com.pb.kemchoaudio.R
import com.pb.kemchoaudio.core.presentation.designsystem.dropdowns.Selectable
import com.pb.kemchoaudio.core.presentation.designsystem.dropdowns.Selectable.Companion.asUnselectedItems
import com.pb.kemchoaudio.core.presentation.util.UiText
import com.pb.kemchoaudio.ui.models.MoodChipContent
import com.pb.kemchoaudio.ui.models.MoodUi

data class ListScreenState(
    val hasAudioRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilter: Boolean = false,
    val isLoadingData: Boolean = false,
    val moods: List<Selectable<MoodUi>> = emptyList(),
    val topics: List<Selectable<String>> = listOf("Love", "Happy", "Work").asUnselectedItems(),
    val moodChipContent: MoodChipContent = MoodChipContent(),
    val selectedFilterChip: AudioFilterType? = null,
    val topicChipTitle: UiText = UiText.StringResource(R.string.all_topics)
)