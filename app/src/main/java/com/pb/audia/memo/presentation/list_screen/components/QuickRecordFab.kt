package com.pb.audia.memo.presentation.list_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.pb.audia.core.presentation.designsystem.theme.AppTheme
import com.pb.audia.core.presentation.designsystem.theme.Microphone
import com.pb.audia.core.presentation.designsystem.theme.buttonGradiant
import com.pb.audia.core.presentation.designsystem.theme.buttonGradiantPressed
import com.pb.audia.core.presentation.designsystem.theme.primary90
import com.pb.audia.core.presentation.designsystem.theme.primary95
import com.pb.audia.memo.presentation.models.rememberBubbleFabColors
import kotlin.math.roundToInt

@Composable
fun QuickRecordFab(
    isQuickRecording: Boolean,
    onLongPressStart: () -> Unit,
    onLongPressEnd: (isCancelled: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val cancelButtonOffset = -100.dp
    val cancelButtonOffsetPx = with(LocalDensity.current) {
        cancelButtonOffset.toPx()
    }
    var dragOffsetX by remember { mutableFloatStateOf(0f) }
    var needToHandleLongClickEnd by remember { mutableStateOf(false) }
    val isWithinCancelThreshold by remember(cancelButtonOffsetPx) {
        derivedStateOf {
            dragOffsetX <= cancelButtonOffsetPx * 0.8f
        }
    }

    LaunchedEffect(isWithinCancelThreshold) {
        if (isWithinCancelThreshold) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    val fabPositionOffSet by remember {
        derivedStateOf {
            IntOffset(
                x = dragOffsetX.toInt().coerceIn(
                    minimumValue = cancelButtonOffsetPx.roundToInt(), maximumValue = 0
                ), y = 0
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center, modifier = modifier.pointerInput(Unit) {
                detectDragGesturesAfterLongPress(onDragStart = {
                    needToHandleLongClickEnd = true
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPressStart()
                }, onDragEnd = {
                    if (needToHandleLongClickEnd) {
                        needToHandleLongClickEnd = false
                        onLongPressEnd(isWithinCancelThreshold)
                        dragOffsetX = 0f
                    }
                }, onDragCancel = {
                    if (needToHandleLongClickEnd) {
                        needToHandleLongClickEnd = false
                        onLongPressEnd(isWithinCancelThreshold)
                        dragOffsetX = 0f
                    }
                }, onDrag = { change, dragAmount ->
                    dragOffsetX += change.positionChange().x
                })
            }) {
        if (isQuickRecording) {
            Box(
                modifier = Modifier
                    .offset(x = cancelButtonOffset)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "cancel recording",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        MemoBubbleFab(
            modifier = Modifier.offset { fabPositionOffSet },
            showBubble = isQuickRecording,
            onClick = onClick,
            icon = {
                Icon(
                    imageVector = if (isQuickRecording) Icons.Filled.Microphone else Icons.Default.Add,
                    contentDescription = if (isQuickRecording) "Finish recording" else "add new entry",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            colors = rememberBubbleFabColors(
                primary = if (isWithinCancelThreshold) {
                    SolidColor(Color.Red)
                } else MaterialTheme.colorScheme.buttonGradiant,
                primaryPressed = MaterialTheme.colorScheme.buttonGradiantPressed,
                innerColor = if (isWithinCancelThreshold) {
                    SolidColor(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f))
                } else SolidColor(MaterialTheme.colorScheme.primary90),
                outerColor = if (isWithinCancelThreshold) {
                    SolidColor(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f))
                } else SolidColor(MaterialTheme.colorScheme.primary95),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        QuickRecordFab(
            isQuickRecording = true,
            onLongPressStart = {},
            onLongPressEnd = {},
            onClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}