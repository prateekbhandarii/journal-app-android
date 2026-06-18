@file:OptIn(ExperimentalFoundationApi::class)

package com.pb.audia.memo.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pb.audia.core.presentation.designsystem.theme.AppTheme
import com.pb.audia.core.presentation.util.UiText
import com.pb.audia.memo.presentation.models.MoodUi
import com.pb.audia.memo.presentation.models.PlaybackState
import com.pb.audia.memo.presentation.models.MemoSection
import com.pb.audia.memo.presentation.models.MemoRelativePosition
import com.pb.audia.memo.presentation.models.MemoUi
import com.pb.audia.memo.presentation.models.TrackSizeInfo
import java.time.Instant
import java.time.ZonedDateTime
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@Composable
fun MemoTimeLine(
    modifier: Modifier = Modifier,
    sections: List<com.pb.audia.memo.presentation.models.MemoSection>,
    onPlayClick: (id: Int) -> Unit,
    onPauseClick: (id: Int) -> Unit,
    onTrackSizeAvailable: (com.pb.audia.memo.presentation.models.TrackSizeInfo) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        sections.forEachIndexed { index, (header, records) ->
            stickyHeader {
                if (index > 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Text(
                    text = header.asString().uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(
                items = records,
                key = { _, item -> item.id },
            ) { index, record ->
                _root_ide_package_.com.pb.audia.memo.presentation.components.MemoTimeLineItem(
                    memoUi = record,
                    onPlayClick = { onPlayClick(record.id) },
                    onPauseClick = { onPauseClick(record.id) },
                    onTrackSizeAvailable = onTrackSizeAvailable,
                    relativePosition = when {
                        index == 0 && records.size == 1 -> _root_ide_package_.com.pb.audia.memo.presentation.models.MemoRelativePosition.SINGLE_ENTRY
                        index == 0 -> _root_ide_package_.com.pb.audia.memo.presentation.models.MemoRelativePosition.FIRST
                        records.lastIndex == index -> _root_ide_package_.com.pb.audia.memo.presentation.models.MemoRelativePosition.LAST
                        else -> _root_ide_package_.com.pb.audia.memo.presentation.models.MemoRelativePosition.IN_BETWEEN
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoTimeLinePreview() {
    AppTheme {
        _root_ide_package_.com.pb.audia.memo.presentation.components.MemoTimeLine(
            sections = listOf(
                _root_ide_package_.com.pb.audia.memo.presentation.models.MemoSection(
                    dateHeader = UiText.DynamicString("Today"),
                    memos = listOf(
                        _root_ide_package_.com.pb.audia.memo.presentation.models.MemoUi(
                            id = 1,
                            title = "Sample Title",
                            mood = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.STRESSED,
                            recordedAt = Instant.now(),
                            note = "Hello this is a sample note to test the expandable text functionality. Let's make it long enough to ensure that it overflows and shows the 'Show More' option.",
                            topics = listOf("Topic 1", "Topic 2"),
                            amplitudes = (1..30).map { Random.nextFloat() },
                            playbackCurrentDuration = 120.seconds,
                            playbackTotalDuration = 250.seconds,
                            playbackState = _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING,
                        )
                    )
                ),
                _root_ide_package_.com.pb.audia.memo.presentation.models.MemoSection(
                    dateHeader = UiText.DynamicString("Yesterday"),
                    memos = listOf(
                        _root_ide_package_.com.pb.audia.memo.presentation.models.MemoUi(
                            id = 2,
                            title = "Sample Title",
                            mood = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.PEACEFUL,
                            recordedAt = ZonedDateTime.now().minusDays(1).toInstant(),
                            note = "Hello this is a sample note to test the expandable text functionality. Let's make it long enough to ensure that it overflows and shows the 'Show More' option.",
                            topics = listOf("Topic 1", "Topic 2"),
                            amplitudes = (1..30).map { Random.nextFloat() },
                            playbackCurrentDuration = 120.seconds,
                            playbackTotalDuration = 250.seconds,
                            playbackState = _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING,
                        )
                    )
                ),
                _root_ide_package_.com.pb.audia.memo.presentation.models.MemoSection(
                    dateHeader = UiText.DynamicString("2026/06/11"),
                    memos = (3..5).map {
                        _root_ide_package_.com.pb.audia.memo.presentation.models.MemoUi(
                            id = it,
                            title = "Sample Title $it",
                            mood = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.NEUTRAL,
                            recordedAt = ZonedDateTime.now().minusDays(4).toInstant(),
                            note = "Hello this is a sample note to test the expandable text functionality. Let's make it long enough to ensure that it overflows and shows the 'Show More' option.",
                            topics = listOf("Android", "Interview"),
                            amplitudes = (1..30).map { Random.nextFloat() },
                            playbackCurrentDuration = 120.seconds,
                            playbackTotalDuration = 250.seconds,
                            playbackState = _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING,
                        )
                    }
                )
            ),
            onPlayClick = {},
            onPauseClick = {},
            onTrackSizeAvailable = {}
        )
    }
}