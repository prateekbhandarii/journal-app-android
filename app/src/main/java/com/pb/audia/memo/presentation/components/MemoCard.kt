@file:OptIn(ExperimentalLayoutApi::class)

package com.pb.audia.memo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pb.audia.core.presentation.designsystem.chips.HashTagChip
import com.pb.audia.core.presentation.designsystem.theme.AppTheme
import com.pb.audia.core.presentation.util.defaultShadow
import com.pb.audia.memo.presentation.models.MoodUi
import com.pb.audia.memo.presentation.models.PlaybackState
import com.pb.audia.memo.presentation.models.MemoUi
import com.pb.audia.memo.presentation.models.TrackSizeInfo
import java.time.Instant
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@Composable
fun MemoCard(
    memoUi: com.pb.audia.memo.presentation.models.MemoUi,
    modifier: Modifier = Modifier,
    onTrackSizeAvailable: (com.pb.audia.memo.presentation.models.TrackSizeInfo) -> Unit,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier.defaultShadow(shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = memoUi.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = memoUi.formattedRecordedAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            _root_ide_package_.com.pb.audia.memo.presentation.components.MoodPlayer(
                moodUi = memoUi.mood,
                playbackState = memoUi.playbackState,
                onPlayClick = onPlayClick,
                onPauseClick = onPauseClick,
                playerProgress = {
                    memoUi.playbackRatio
                },
                durationPlayed = memoUi.playbackCurrentDuration,
                totalPlaybackDuration = memoUi.playbackTotalDuration,
                powerRatio = memoUi.amplitudes,
                onTrackSizeAvailable = onTrackSizeAvailable
            )

            if (memoUi.note.isNotBlank()) {
                _root_ide_package_.com.pb.audia.memo.presentation.components.ExpandableText(
                    text = memoUi.note
                )
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                memoUi.topics.forEach { topic ->
                    HashTagChip(text = topic)
                }
            }
        }
    }
}

@Preview
@Composable
private fun MemoCardPreview() {
    AppTheme {
        _root_ide_package_.com.pb.audia.memo.presentation.components.MemoCard(
            memoUi = _root_ide_package_.com.pb.audia.memo.presentation.models.MemoUi(
                id = 0,
                title = "Sample Title",
                mood = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.STRESSED,
                recordedAt = Instant.now(),
                note = "Hello this is a sample note to test the expandable text functionality. Let's make it long enough to ensure that it overflows and shows the 'Show More' option.",
                topics = listOf("Topic 1", "Topic 2"),
                amplitudes = (1..30).map { Random.nextFloat() },
                playbackCurrentDuration = 120.seconds,
                playbackTotalDuration = 250.seconds,
                playbackState = _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING,
            ),
            onTrackSizeAvailable = {},
            onPlayClick = {},
            onPauseClick = {}
        )
    }
}