package com.pb.audia.memo.presentation.list_screen.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pb.audia.memo.presentation.models.BubbleFabColors
import com.pb.audia.memo.presentation.models.rememberBubbleFabColors

@Composable
fun MemoBubbleFab(
    showBubble: Boolean,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    colors: BubbleFabColors = rememberBubbleFabColors(),
    primaryButtonSize: Dp = 56.dp,
    onClick: () -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()
    Box(
        modifier = modifier
            .background(
                brush = if (showBubble) {
                    colors.outerColor
                } else {
                    SolidColor(Color.Transparent)
                },
                shape = CircleShape
            )
            .padding(10.dp)
            .background(
                brush = if (showBubble) {
                    colors.innerColor
                } else {
                    SolidColor(Color.Transparent)
                },
                shape = CircleShape
            )
            .padding(16.dp)
            .background(
                brush = if (isPressed) {
                    colors.primaryPressed
                } else {
                    colors.primary
                },
                shape = CircleShape
            )
            .size(primaryButtonSize)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}