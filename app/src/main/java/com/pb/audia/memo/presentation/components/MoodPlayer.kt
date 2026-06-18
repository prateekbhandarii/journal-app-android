package com.pb.audia.memo.presentation.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pb.audia.core.presentation.designsystem.theme.AppTheme
import com.pb.audia.core.presentation.designsystem.theme.MoodPrimary25
import com.pb.audia.core.presentation.designsystem.theme.MoodPrimary35
import com.pb.audia.core.presentation.designsystem.theme.MoodPrimary80
import com.pb.audia.core.presentation.util.formatMMSS
import com.pb.audia.memo.presentation.models.MoodUi
import com.pb.audia.memo.presentation.models.PlaybackState
import com.pb.audia.memo.presentation.models.TrackSizeInfo
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun MoodPlayer(
    modifier: Modifier = Modifier,
    moodUi: com.pb.audia.memo.presentation.models.MoodUi?,
    playbackState: com.pb.audia.memo.presentation.models.PlaybackState,
    playerProgress: () -> Float,
    totalPlaybackDuration: Duration,
    durationPlayed: Duration = Duration.ZERO,
    powerRatio: List<Float>,
    amplitudeBarWidth: Dp = 5.dp,
    amplitudeBarSpacing: Dp = 4.dp,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onTrackSizeAvailable: (com.pb.audia.memo.presentation.models.TrackSizeInfo) -> Unit
) {
    val iconTint = when (moodUi) {
        null -> MoodPrimary80
        else -> moodUi.colorSet.vivid
    }

    val trackFillColor = when (moodUi) {
        null -> MoodPrimary80
        else -> moodUi.colorSet.vivid
    }

    val backgroundColor = when (moodUi) {
        null -> MoodPrimary25
        else -> moodUi.colorSet.faded
    }

    val trackColor = when (moodUi) {
        null -> MoodPrimary35
        else -> moodUi.colorSet.desaturated
    }

    val formattedDurationText = remember(durationPlayed, totalPlaybackDuration) {
        "${durationPlayed.formatMMSS()}/${totalPlaybackDuration.formatMMSS()}"
    }

    Surface(
        shape = CircleShape,
        color = backgroundColor,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            _root_ide_package_.com.pb.audia.memo.presentation.components.PlaybackButton(
                playbackState = playbackState,
                onPlayClick = onPlayClick,
                onPauseClick = onPauseClick,
                colors = IconButtonDefaults.filledIconButtonColors(
                    contentColor = iconTint,
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )

            _root_ide_package_.com.pb.audia.memo.presentation.components.MemoPlayBar(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        vertical = 10.dp,
                        horizontal = 8.dp
                    )
                    .fillMaxHeight(),
                amplitudeBarWidth = amplitudeBarWidth,
                amplitudeBarSpacing = amplitudeBarSpacing,
                powerRatio = powerRatio,
                trackColor = trackColor,
                trackFillColor = trackFillColor,
                playerProgress = playerProgress
            )

            Text(
                text = formattedDurationText,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun MoodPlayerPreview() {
    AppTheme {

        val testRatio = (1..30).map {
            Random.nextFloat()
        }

        _root_ide_package_.com.pb.audia.memo.presentation.components.MoodPlayer(
            moodUi = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.NEUTRAL,
            playbackState = _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING,
            playerProgress = { 0.5f },
            totalPlaybackDuration = 250.seconds,
            powerRatio = testRatio,
            onPlayClick = {},
            onPauseClick = {},
            onTrackSizeAvailable = {}
        )
    }
}