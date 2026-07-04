package com.pb.audia.memo.presentation.models

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import com.pb.audia.core.presentation.designsystem.theme.buttonGradiant
import com.pb.audia.core.presentation.designsystem.theme.buttonGradiantPressed
import com.pb.audia.core.presentation.designsystem.theme.primary90
import com.pb.audia.core.presentation.designsystem.theme.primary95

data class BubbleFabColors(
    val primary: Brush,
    val primaryPressed: Brush,
    val outerColor: Brush,
    val innerColor: Brush,
)

@Composable
fun rememberBubbleFabColors(
    primary: Brush = MaterialTheme.colorScheme.buttonGradiant,
    primaryPressed: Brush = MaterialTheme.colorScheme.buttonGradiantPressed,
    outerColor: Brush = SolidColor(MaterialTheme.colorScheme.primary95),
    innerColor: Brush = SolidColor(MaterialTheme.colorScheme.primary90)
): BubbleFabColors {
    return remember(primary, primaryPressed, outerColor, innerColor) {
        BubbleFabColors(
            primary = primary,
            primaryPressed = primaryPressed,
            outerColor = outerColor,
            innerColor = innerColor
        )
    }
}
