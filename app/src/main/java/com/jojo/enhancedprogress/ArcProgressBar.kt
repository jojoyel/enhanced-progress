package com.jojo.enhancedprogress

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ArcProgressBar(
    modifier: Modifier = Modifier,
    progress: List<ProgressData>,
    backgroundColor: Color = Color.Transparent,
    radius: Dp = 50.dp,
    stroke: Dp = 2.dp,
    baseAngle: Float = 150f
) {

    val calculatedProgresses = List(progress.size) {
        progress[it].progress * 100f * 2.4f
    }

    val animatedBase = remember {
        Animatable(0f)
    }

    val animatedProgresses = progress.map {
        remember {
            Animatable(0f)
        }
    }

    LaunchedEffect(key1 = true) {
        animatedBase.animateTo(240f)
    }

    LaunchedEffect(key1 = progress) {
        animatedProgresses.forEachIndexed { index, it ->
            it.animateTo(calculatedProgresses[index])
        }
    }

    Canvas(modifier = modifier.size(radius * 2f)) {

        // Drawing background
        drawCircle(color = backgroundColor, radius = radius.toPx() / 3, alpha = .4f)

        // Drawing base
        drawArc(
            Color.Gray,
            baseAngle,
            animatedBase.value,
            useCenter = false,
            style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
        )

        animatedProgresses.forEachIndexed { index, it ->
            val lastSweep = if (index > 0) {
                var i = 0f
                for (j in 0 until index) {
                    i += calculatedProgresses[j]
                }
                i
            } else 0f

            when (index) {
                0 -> {
                    // First progress part
                    drawArc(
                        color = progress[index].color,
                        startAngle = baseAngle,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = progress[index].color,
                        startAngle = baseAngle + it.value / 2,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx())
                    )
                }
                animatedProgresses.size - 1 -> {
                    // Last progress part
                    drawArc(
                        color = progress[index].color,
                        startAngle = baseAngle,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx())
                    )
                    drawArc(
                        color = progress[index].color,
                        startAngle = baseAngle + it.value / 2,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                    )
                }
                else -> drawArc(
                    color = progress[index].color,
                    startAngle = baseAngle + lastSweep,
                    sweepAngle = it.value,
                    useCenter = false,
                    style = Stroke(width = stroke.toPx())
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArcProgressBarPreview() {
    ArcProgressBar(
        progress = listOf(
            ProgressData(.2f, Color.Red),
            ProgressData(.4f, Color.Green)
        )
    )
}