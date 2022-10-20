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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ArcProgressBar(
    modifier: Modifier = Modifier,
    progresses: List<ProgressData>,
    backgroundColor: Color = Color.Transparent,
    radius: Dp = 50.dp,
    stroke: Dp = 2.dp
) {

    val calculatedProgresses = List(progresses.size) {
        progresses[it].progress * 100f * 2.4f
    }

    val animatedBase = remember {
        Animatable(0f)
    }

    val animatedProgresses = progresses.map {
        remember {
            Animatable(0f)
        }
    }

    LaunchedEffect(key1 = true) {
        animatedBase.animateTo(240f)
    }

    LaunchedEffect(key1 = progresses) {
        animatedProgresses.forEachIndexed { index, it ->
            it.animateTo(
                calculatedProgresses[index]
            )
        }
    }

    Canvas(modifier = modifier.size(radius * 2f)) {
        drawCircle(color = backgroundColor, radius = radius.toPx() / 3, alpha = .4f)
        drawArc(
            Color.Gray,
            150f,
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
                    drawArc(
                        color = progresses[index].color,
                        startAngle = 150f,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = progresses[index].color,
                        startAngle = 150f + it.value / 2,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx())
                    )
                }
                animatedProgresses.size - 1 -> {
                    drawArc(
                        color = progresses[index].color,
                        startAngle = 150f + lastSweep,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx())
                    )
                    drawArc(
                        color = progresses[index].color,
                        startAngle = 150f + lastSweep + it.value / 2,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                    )
                }
                else -> drawArc(
                    color = progresses[index].color,
                    startAngle = 150f + lastSweep,
                    sweepAngle = it.value,
                    useCenter = false,
                    style = Stroke(width = stroke.toPx())
                )
            }
        }
    }
}