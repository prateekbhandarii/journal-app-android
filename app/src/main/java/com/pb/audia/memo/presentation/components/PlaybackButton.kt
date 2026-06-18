package com.pb.audia.memo.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pb.audia.core.presentation.designsystem.theme.AppTheme
import com.pb.audia.core.presentation.designsystem.theme.Pause
import com.pb.audia.core.presentation.util.defaultShadow
import com.pb.audia.memo.presentation.models.MoodUi
import com.pb.audia.memo.presentation.models.PlaybackState

@Composable
fun PlaybackButton(
    modifier: Modifier = Modifier,
    playbackState: com.pb.audia.memo.presentation.models.PlaybackState,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    colors: IconButtonColors
) {
    FilledIconButton(
        onClick = when (playbackState) {
            _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING -> onPauseClick
            _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PAUSED,
            _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.STOPPED -> onPlayClick
        },
        colors = colors,
        modifier = modifier.defaultShadow()
    ) {
        Icon(
            imageVector = when (playbackState) {
                _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING -> Icons.Filled.Pause
                _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PAUSED,
                _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.STOPPED -> Icons.Filled.PlayArrow
            },
            contentDescription = when (playbackState) {
                _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING -> "pause"
                _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PAUSED,
                _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.STOPPED -> "play"
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaybackButtonPreview() {
    AppTheme {
        _root_ide_package_.com.pb.audia.memo.presentation.components.PlaybackButton(
            playbackState = _root_ide_package_.com.pb.audia.memo.presentation.models.PlaybackState.PLAYING,
            onPlayClick = {},
            onPauseClick = {},
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.NEUTRAL.colorSet.faded,
                contentColor = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.NEUTRAL.colorSet.vivid
            )
        )
    }
}