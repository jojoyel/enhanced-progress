package com.jojo.enhancedprogress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import kotlin.math.max

/**
 * Displays a straight progress bar with different progress given in list of [ProgressData]
 * <p>
 * @see [ProgressData]
 * @param progress A list of progress to be displayed on the arc, see [ProgressData]
 * @param stroke The thickness of the bars
 */
@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    progress: List<ProgressData>,
    stroke: Dp = 5.dp
) {

    val p = progress.sortedBy { it.progress }.reversed()

    val pxHeight = with(LocalDensity.current) { stroke.value.dp.toPx() }

    val animatedStroke = remember {
        Animatable(0f)
    }

    val animatedProgresses = p.map {
        remember {
            Animatable(0f)
        }
    }

    LaunchedEffect(key1 = true) {
        animatedStroke.animateTo(pxHeight)
    }

    LaunchedEffect(progress) {
        animatedProgresses.forEachIndexed { index, it ->
            async {
                it.animateTo(p[index].progress * 100, tween(delayMillis = index * 20))
            }
        }
    }

    Canvas(
        modifier = Modifier
            .height(stroke)
            .then(modifier)
    ) {
        drawLine(
            color = Color.Gray,
            start = Offset(0f + pxHeight, pxHeight / 2),
            end = Offset(size.width - pxHeight / 2, pxHeight / 2),
            strokeWidth = animatedStroke.value,
            cap = StrokeCap.Round
        )
        animatedProgresses.forEachIndexed { index, it ->
            drawLine(
                color = p[index].color,
                start = Offset(0f + pxHeight / 2, pxHeight / 2),
                end = Offset(max((it.value * size.width / 100) - pxHeight / 2, 0f), pxHeight / 2),
                strokeWidth = pxHeight,
                cap = StrokeCap.Round
            )
        }
    }
}
