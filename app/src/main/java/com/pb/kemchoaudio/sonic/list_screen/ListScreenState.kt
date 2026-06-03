package com.pb.kemchoaudio.sonic.list_screen

import com.pb.kemchoaudio.R
import com.pb.kemchoaudio.core.presentation.designsystem.dropdowns.Selectable
import com.pb.kemchoaudio.core.presentation.designsystem.dropdowns.Selectable.Companion.asUnselectedItems
import com.pb.kemchoaudio.core.presentation.util.UiText
import com.pb.kemchoaudio.sonic.list_screen.models.MoodChipContent
import com.pb.kemchoaudio.sonic.list_screen.models.MoodUi

data class ListScreenState(
    val hasAudioRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilter: Boolean = false,
    val isLoadingData: Boolean = false,
    val moods: List<Selectable<MoodUi>> = emptyList(),
    val topics: List<Selectable<String>> = listOf("Love", "Happy", "Work").asUnselectedItems(),
    val moodChipContent: MoodChipContent = MoodChipContent(),
    val selectedFilterChip: SonicFilterType? = null,
    val topicChipTitle: UiText = UiText.StringResource(R.string.all_topics)
)