package com.pb.audia.memo.list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pb.audia.R
import com.pb.audia.core.presentation.designsystem.dropdowns.Selectable
import com.pb.audia.core.presentation.util.UiText
import com.pb.audia.memo.models.MoodChipContent
import com.pb.audia.memo.models.MoodUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MemoListViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MemoListScreenState())
    private val selectedMoodFilters = MutableStateFlow<List<MoodUi>>(emptyList())
    private val selectedTopicFilters = MutableStateFlow<List<String>>(emptyList())

    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeFilters()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MemoListScreenState()
        )

    fun onAction(action: MemoListScreenAction) {
        when (action) {
            MemoListScreenAction.OnFabClick -> {}
            MemoListScreenAction.OnFabLongClick -> {}
            MemoListScreenAction.OnMoodChipClick -> {
                _state.update { it.copy(
                    selectedFilterChip = MemoFilterType.MOODS,
                ) }
            }
            is MemoListScreenAction.OnRemoveFilters -> {
                when(action.filterType) {
                    MemoFilterType.MOODS -> {
                        selectedMoodFilters.value = emptyList()
                    }

                    MemoFilterType.TOPICS -> {
                        selectedTopicFilters.value = emptyList()
                    }
                }
            }
            MemoListScreenAction.OnTopicChipClick -> {
                _state.update { it.copy(
                    selectedFilterChip = MemoFilterType.TOPICS,
                ) }
            }
            MemoListScreenAction.OnSettingsClick -> {}

            MemoListScreenAction.OnDismissTopicChipDropdown,
            MemoListScreenAction.OnDismissMoodChipDropdown -> {
                _state.update {
                    it.copy(selectedFilterChip = null)
                }
            }

            is MemoListScreenAction.OnFilterByMoodClick -> {
                toggleMoodFilter(action.moodUi)
            }

            is MemoListScreenAction.OnFilterByTopicClick -> {
                toggleTopicFilter(action.topic)
            }

            is MemoListScreenAction.OnMemoPauseClick -> TODO()
            is MemoListScreenAction.OnMemoPlayClick -> TODO()
            is MemoListScreenAction.OnTrackSizeAvailable -> TODO()
        }
    }

    private fun toggleTopicFilter(topic: String) {
        selectedTopicFilters.update { selectedTopics ->
            if (topic in selectedTopics) {
                selectedTopics - topic
            } else {
                selectedTopics + topic
            }
        }
    }

    private fun toggleMoodFilter(moodUi: MoodUi) {
        selectedMoodFilters.update { selectedMoods ->
            if (moodUi in selectedMoods) {
                selectedMoods - moodUi
            } else {
                selectedMoods + moodUi
            }
        }
    }

    private fun observeFilters() {
        combine(
            selectedTopicFilters,
            selectedMoodFilters
        ) { selectedTopics, selectedMoods ->
            _state.update {
                it.copy(
                    topics = it.topics.map { selectableTopic ->
                        Selectable(
                            item = selectableTopic.item,
                            selected = selectedTopics.contains(selectableTopic.item)
                        )
                    },
                    moods = MoodUi.entries.map { mood ->
                        Selectable(
                            item = mood,
                            selected = selectedMoods.contains(mood)
                        )
                    },
                    hasActiveMoodFilter = selectedMoods.isNotEmpty(),
                    hasActiveTopicFilters = selectedTopics.isNotEmpty(),
                    topicChipTitle = selectedTopics.deriveTopicChipTitle(),
                    moodChipContent = selectedMoods.asMoodChipContent()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun List<String>.deriveTopicChipTitle(): UiText {
        return when (size) {
            0 -> UiText.StringResource(R.string.all_topics)
            1 -> UiText.DynamicString(this.first())
            2 -> UiText.DynamicString("${this.first()}, ${this.last()}")
            else -> {
                val extraElementCount = size - 2
                UiText.DynamicString("${this.first()}, ${this[1]} +$extraElementCount")
            }
        }
    }

    private fun List<MoodUi>.asMoodChipContent(): MoodChipContent {
        if (this.isEmpty()) {
            return MoodChipContent()
        }

        val icons = this.map { it.iconSet.filled }
        val moodNames = this.map { it.title }

        return when (size) {
            1 -> {
                MoodChipContent(icons, moodNames.first())
            }

            2 -> {
                MoodChipContent(
                    icons,
                    UiText.Combined(
                        format = "%s, %s",
                        uiTexts = moodNames.toTypedArray()
                    )
                )
            }

            else -> {
                val extraElementCount = size - 2
                MoodChipContent(
                    icons,
                    UiText.Combined(
                        format = "%s, %s +$extraElementCount",
                        uiTexts = moodNames.take(2).toTypedArray()
                    )
                )
            }
        }
    }
}