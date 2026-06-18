package com.pb.audia.memo.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pb.audia.core.presentation.designsystem.theme.AppTheme
import com.pb.audia.memo.presentation.models.MoodUi
import kotlin.random.Random

@Composable
fun MemoPlayBar(
    modifier: Modifier = Modifier,
    amplitudeBarWidth: Dp,
    amplitudeBarSpacing: Dp,
    powerRatio: List<Float>,
    trackColor: Color,
    trackFillColor: Color,
    playerProgress: () -> Float
) {
    Canvas(
        modifier = modifier
    ) {
        val amplitudeBarWidthPx = amplitudeBarWidth.toPx()
        val amplitudeBarSpacingPx = amplitudeBarSpacing.toPx()

        val clipPath = Path()

        powerRatio.forEachIndexed { index, ratio ->
            val height = ratio * size.height
            val xOffset = index * (amplitudeBarSpacingPx + amplitudeBarWidthPx)
            val yTopStart = center.y - height / 2f

            val topLeft = Offset(xOffset, yTopStart)
            val rectSize = Size(
                width = amplitudeBarWidthPx,
                height = height
            )

            val roundRect = RoundRect(
                rect = Rect(
                    offset = topLeft,
                    size = rectSize
                ),
                cornerRadius = CornerRadius(100f)
            )

            clipPath.addRoundRect(roundRect)

            drawRoundRect(
                color = trackColor,
                topLeft = topLeft,
                size = rectSize,
                cornerRadius = CornerRadius(100f)
            )

            clipPath(clipPath) {
                drawRect(
                    color = trackFillColor,
                    size = Size(
                        width = size.width * playerProgress(),
                        height = size.height
                    )
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoPlayBarPreview() {
    AppTheme {

        val testRatio = (1..30).map {
            Random.nextFloat()
        }

        _root_ide_package_.com.pb.audia.memo.presentation.components.MemoPlayBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            amplitudeBarWidth = 4.dp,
            amplitudeBarSpacing = 2.dp,
            powerRatio = testRatio,
            trackColor = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.NEUTRAL.colorSet.desaturated,
            trackFillColor = _root_ide_package_.com.pb.audia.memo.presentation.models.MoodUi.NEUTRAL.colorSet.vivid,
            playerProgress = { 0.3f }
        )
    }
}