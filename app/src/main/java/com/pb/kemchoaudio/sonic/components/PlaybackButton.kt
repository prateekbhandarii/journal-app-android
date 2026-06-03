package com.pb.kemchoaudio.sonic.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pb.kemchoaudio.core.presentation.designsystem.theme.KemChoAudioTheme
import com.pb.kemchoaudio.core.presentation.designsystem.theme.Pause
import com.pb.kemchoaudio.core.presentation.util.defaultShadow
import com.pb.kemchoaudio.sonic.list_screen.models.MoodUi
import com.pb.kemchoaudio.sonic.list_screen.models.PlaybackState

@Composable
fun PlaybackButton(
    modifier: Modifier = Modifier,
    playbackState: PlaybackState,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    colors: IconButtonColors
) {
    FilledIconButton(
        onClick = when (playbackState) {
            PlaybackState.PLAYING -> onPauseClick
            PlaybackState.PAUSED,
            PlaybackState.STOPPED -> onPlayClick
        },
        colors = colors,
        modifier = modifier.defaultShadow()
    ) {
        Icon(
            imageVector = when (playbackState) {
                PlaybackState.PLAYING -> Icons.Filled.Pause
                PlaybackState.PAUSED,
                PlaybackState.STOPPED -> Icons.Filled.PlayArrow
            },
            contentDescription = when (playbackState) {
                PlaybackState.PLAYING -> "pause"
                PlaybackState.PAUSED,
                PlaybackState.STOPPED -> "play"
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaybackButtonPreview() {
    KemChoAudioTheme {
        PlaybackButton(
            playbackState = PlaybackState.PLAYING,
            onPlayClick = {},
            onPauseClick = {},
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MoodUi.NEUTRAL.colorSet.faded,
                contentColor = MoodUi.NEUTRAL.colorSet.vivid
            )
        )
    }
}