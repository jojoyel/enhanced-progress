package com.jojo.enhancedprogress

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
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
    baseAngle: Float = 150f,
    endAngle: Float = 240f,
    insideCap: StrokeCap = StrokeCap.Round
) {

    val sorted = progress.toMutableList().sortedByDescending { it.progress }

    val calculatedProgresses = List(sorted.size) {
        sorted[it].progress * endAngle
    }

    val animatedBase = remember {
        Animatable(0f)
    }

    val animatedProgresses = sorted.map {
        remember {
            Animatable(0f)
        }
    }

    LaunchedEffect(key1 = true) {
        animatedBase.animateTo(endAngle)
    }

    LaunchedEffect(key1 = sorted) {
        animatedProgresses.forEachIndexed { index, it ->
            it.animateTo(calculatedProgresses[index])
        }
    }

    Canvas(modifier = Modifier.size(radius * 2f).padding(stroke / 2).then(modifier)) {

        // Drawing background
        drawCircle(color = backgroundColor, radius = radius.toPx() / 2, alpha = .4f)

        // Drawing base
        drawArc(
            Color.Gray,
            baseAngle,
            animatedBase.value,
            useCenter = false,
            style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
        )

        animatedProgresses.forEachIndexed { index, it ->
            when (index) {
                0 -> {
                    drawArc(
                        color = sorted[index].color,
                        startAngle = baseAngle,
                        sweepAngle = it.value,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                    )
                }
                else -> {
                    drawArc(
                        color = sorted[index].color,
                        startAngle = baseAngle,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = sorted[index].color,
                        startAngle = baseAngle + it.value / 2,
                        sweepAngle = it.value / 2,
                        useCenter = false,
                        style = Stroke(width = stroke.toPx(), cap = insideCap)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArcProgressBarPreview() {
    ArcProgressBar(
        progress = listOf(
            ProgressData(.4f, Color.Green),
            ProgressData(.2f, Color.Red),
        ), stroke = 5.dp,
    )
}