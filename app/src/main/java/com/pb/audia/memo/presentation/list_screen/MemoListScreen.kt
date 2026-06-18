package com.pb.audia.memo.presentation.list_screen

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
import com.pb.audia.core.presentation.designsystem.theme.AppTheme
import com.pb.audia.core.presentation.designsystem.theme.bgGradiant
import com.pb.audia.memo.presentation.components.MemoTimeLine
import com.pb.audia.memo.presentation.list_screen.components.EmptyStateBackground
import com.pb.audia.memo.presentation.list_screen.components.FilterRow
import com.pb.audia.memo.presentation.list_screen.components.MemoTopBar
import com.pb.audia.memo.presentation.list_screen.components.RecordFloatingButton

@Composable
fun MemoListScreenRoot(
    viewModel: com.pb.audia.memo.presentation.list_screen.MemoListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    _root_ide_package_.com.pb.audia.memo.presentation.list_screen.MemoListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun MemoListScreen(
    state: com.pb.audia.memo.presentation.list_screen.MemoListScreenState,
    onAction: (com.pb.audia.memo.presentation.list_screen.MemoListScreenAction) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            _root_ide_package_.com.pb.audia.memo.presentation.list_screen.components.RecordFloatingButton(
                onClick = {
                    onAction(_root_ide_package_.com.pb.audia.memo.presentation.list_screen.MemoListScreenAction.OnFabClick)
                }
            )
        },
        topBar = {
            _root_ide_package_.com.pb.audia.memo.presentation.list_screen.components.MemoTopBar(
                onSettingsClick = {
                    onAction(_root_ide_package_.com.pb.audia.memo.presentation.list_screen.MemoListScreenAction.OnSettingsClick)
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

            _root_ide_package_.com.pb.audia.memo.presentation.list_screen.components.FilterRow(
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
                    _root_ide_package_.com.pb.audia.memo.presentation.list_screen.components.EmptyStateBackground(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                }

                else -> {
                    _root_ide_package_.com.pb.audia.memo.presentation.components.MemoTimeLine(
                        sections = state.memoList,
                        modifier = Modifier.fillMaxSize(),
                        onPlayClick = {
                            onAction(
                                _root_ide_package_.com.pb.audia.memo.presentation.list_screen.MemoListScreenAction.OnMemoPlayClick(
                                    it
                                )
                            )
                        },
                        onPauseClick = {
                            onAction(
                                _root_ide_package_.com.pb.audia.memo.presentation.list_screen.MemoListScreenAction.OnMemoPauseClick(
                                    it
                                )
                            )
                        },
                        onTrackSizeAvailable = { trackSizeInfo ->
                            onAction(
                                _root_ide_package_.com.pb.audia.memo.presentation.list_screen.MemoListScreenAction.OnTrackSizeAvailable(
                                    trackSizeInfo
                                )
                            )
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        _root_ide_package_.com.pb.audia.memo.presentation.list_screen.MemoListScreen(
            state = _root_ide_package_.com.pb.audia.memo.presentation.list_screen.MemoListScreenState(
                isLoadingData = false,
                hasAudioRecorded = false
            ),
            onAction = {}
        )
    }
}