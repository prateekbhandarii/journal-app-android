package com.pb.audia.memo.list_screen

import com.pb.audia.R
import com.pb.audia.core.presentation.designsystem.dropdowns.Selectable
import com.pb.audia.core.presentation.designsystem.dropdowns.Selectable.Companion.asUnselectedItems
import com.pb.audia.core.presentation.util.UiText
import com.pb.audia.memo.models.MemoSection
import com.pb.audia.memo.models.MemoUi
import com.pb.audia.memo.models.MoodChipContent
import com.pb.audia.memo.models.MoodUi

data class MemoListScreenState(
    val memoMap: Map<UiText, List<MemoUi>> = emptyMap(),
    val hasAudioRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilter: Boolean = false,
    val isLoadingData: Boolean = false,
    val moods: List<Selectable<MoodUi>> = emptyList(),
    val topics: List<Selectable<String>> = listOf("Love", "Happy", "Work").asUnselectedItems(),
    val moodChipContent: MoodChipContent = MoodChipContent(),
    val selectedFilterChip: MemoFilterType? = null,
    val topicChipTitle: UiText = UiText.StringResource(R.string.all_topics)
) {
    val memoList = memoMap
        .toList()
        .map { (dayText, memo) ->
            MemoSection(
                dateHeader = dayText,
                memos = memo
            )
        }
}