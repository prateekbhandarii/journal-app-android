package com.pb.kemchoaudio.ui.list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pb.kemchoaudio.core.presentation.designsystem.theme.KemChoAudioTheme
import com.pb.kemchoaudio.core.presentation.designsystem.theme.bgGradiant
import com.pb.kemchoaudio.ui.components.AudioTopBar
import com.pb.kemchoaudio.ui.components.EmptyStateBackground
import com.pb.kemchoaudio.ui.components.FilterRow
import com.pb.kemchoaudio.ui.components.RecordFloatingButton

@Composable
fun ListScreenRoot(
    viewModel: ListScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ListScreen(
    state: ListScreenState,
    onAction: (ListScreenAction) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            RecordFloatingButton(
                onClick = {
                    onAction(ListScreenAction.OnFabClick)
                }
            )
        },
        topBar = {
            AudioTopBar(
                onSettingsClick = {
                    onAction(ListScreenAction.OnSettingsClick)
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = MaterialTheme.colorScheme.bgGradiant
                )
                .padding(innerPadding)
        ) {

            FilterRow(
                moodChipContent = state.moodChipContent,
                hasActiveMoodFilters = state.hasActiveMoodFilter,
                selectedAudioFilterChip = state.selectedFilterChip,
                moods = state.moods,
                topicChipTitle = state.topicChipTitle,
                hasActiveTopicFilters = state.hasActiveTopicFilters,
                topics = state.topics,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth()
            )

            when {
                state.isLoadingData -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .wrapContentSize(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                !state.hasAudioRecorded -> {
                    EmptyStateBackground(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    KemChoAudioTheme {
        ListScreen(
            state = ListScreenState(
                isLoadingData = false,
                hasAudioRecorded = false
            ),
            onAction = {}
        )
    }
}