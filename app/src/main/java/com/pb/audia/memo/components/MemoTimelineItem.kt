package com.pb.audia.memo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pb.audia.core.presentation.designsystem.theme.AppTheme
import com.pb.audia.memo.models.MoodUi
import com.pb.audia.memo.models.PlaybackState
import com.pb.audia.memo.models.MemoRelativePosition
import com.pb.audia.memo.models.MemoUi
import com.pb.audia.memo.models.TrackSizeInfo
import java.time.Instant
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

private val noVerticalLineAboveModifier = Modifier.padding(top = 16.dp)
private val noVerticalLineBelowModifier = Modifier.height(8.dp)

@Composable
fun MemoTimeLineItem(
    memoUi: MemoUi,
    modifier: Modifier = Modifier,
    relativePosition: MemoRelativePosition,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onTrackSizeAvailable: (TrackSizeInfo) -> Unit
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (relativePosition != MemoRelativePosition.SINGLE_ENTRY) {
                VerticalDivider(
                    modifier = when (relativePosition) {
                        MemoRelativePosition.FIRST -> noVerticalLineAboveModifier
                        MemoRelativePosition.LAST -> noVerticalLineBelowModifier
                        MemoRelativePosition.IN_BETWEEN -> Modifier
                        else -> Modifier
                    }
                )
            }

            Image(
                imageVector = ImageVector.vectorResource(memoUi.mood.iconSet.filled),
                contentDescription = memoUi.title,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        MemoCard(
            memoUi = memoUi,
            onPlayClick = onPlayClick,
            onPauseClick = onPauseClick,
            onTrackSizeAvailable = onTrackSizeAvailable,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoTimeLineItemPreview() {
    AppTheme {
        MemoTimeLineItem(
            memoUi = MemoUi(
                id = 0,
                title = "Sample Title",
                mood = MoodUi.STRESSED,
                recordedAt = Instant.now(),
                note = "Hello this is a sample note to test the expandable text functionality. Let's make it long enough to ensure that it overflows and shows the 'Show More' option.",
                topics = listOf("Topic 1", "Topic 2"),
                amplitudes = (1..30).map { Random.nextFloat() },
                playbackCurrentDuration = 120.seconds,
                playbackTotalDuration = 250.seconds,
                playbackState = PlaybackState.PLAYING,
            ),
            relativePosition = MemoRelativePosition.LAST,
            onPlayClick = {},
            onPauseClick = {},
            onTrackSizeAvailable = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}