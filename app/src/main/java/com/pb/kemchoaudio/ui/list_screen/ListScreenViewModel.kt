package com.pb.kemchoaudio.ui.list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ListScreenViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ListScreenState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ListScreenState()
        )

    fun onAction(action: ListScreenAction) {
        when (action) {
            ListScreenAction.OnFabClick -> {}
            ListScreenAction.OnFabLongClick -> {}
            ListScreenAction.OnMoodChipClick -> {}
            is ListScreenAction.OnRemoveFilters -> {}
            ListScreenAction.OnTopicChipClick -> {}
            ListScreenAction.OnSettingsClick -> {}
        }
    }

}