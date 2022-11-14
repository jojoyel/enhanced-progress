package com.jojo.enhancedprogress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.isFinished
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import kotlin.math.max

/**
 * Displays a straight progress bar with different progress given in list of [ProgressData]
 * The bar will fill the width defined, to restrict it, set a width in the modifier param or in the parent of this bar
 * <p>
 * @see [ProgressData]
 * @see [AnimationSpecs]
 * @param progress A list of progress to be displayed on the arc, see [ProgressData]
 * @param stroke The thickness of the bars
 * @param backgroundColor The color of the bar in the background
 * @param animationSpecs Options to affect how animations will run, see [AnimationSpecs]
 */
@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    progress: List<ProgressData>,
    stroke: Dp = 5.dp,
    backgroundColor: Color = Color.Gray,
    insideCap: StrokeCap = StrokeCap.Round,
    animationSpecs: AnimationSpecs = AnimationSpecs(),
    animationEnded: () -> Unit = {}
) {
    val p = progress.sortedBy { it.progress }.reversed()

    val pxHeight = with(LocalDensity.current) { stroke.value.dp.toPx() }

    val baseProgress = remember {
        Animatable(0f)
    }

    val animatedProgress = p.map {
        remember {
            Animatable(0f)
        }
    }

    LaunchedEffect(Unit) {
        baseProgress.animateTo(
            1f,
            animationSpec = tween(
                delayMillis = animationSpecs.startDelay,
                durationMillis = animationSpecs.duration
            )
        )
    }

    LaunchedEffect(p) {
        animatedProgress.forEachIndexed { index, it ->
            async {
                val result = it.animateTo(
                    p[index].progress,
                    tween(
                        delayMillis = animationSpecs.startDelay + ((index + 1) * animationSpecs.betweenDelay),
                        durationMillis = animationSpecs.duration
                    )
                ).endState

                when {
                    result.isFinished && index == animatedProgress.size - 1 -> animationEnded()
                    else -> {}
                }
            }
        }
    }

    Canvas(
        modifier = Modifier
            .height(stroke)
            .then(modifier)
    ) {
        if (baseProgress.value > 0)
            drawLine(
                color = backgroundColor,
                start = Offset(0f + pxHeight / 2, pxHeight / 2),
                end = Offset(baseProgress.value * size.width - pxHeight / 2, pxHeight / 2),
                strokeWidth = pxHeight,
                cap = StrokeCap.Round
            )

        animatedProgress.forEachIndexed { index, it ->
            if (it.value > 0) {
                drawLine(
                    color = p[index].color,
                    start = Offset(0f + pxHeight / 2, pxHeight / 2),
                    end = Offset(
                        max(it.value * size.width - pxHeight / 2, 0f),
                        pxHeight / 2
                    ),
                    strokeWidth = pxHeight,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = p[index].color,
                    start = Offset(it.value / 2 + pxHeight / 2, pxHeight / 2),
                    end = Offset(
                        max(it.value / 2 * size.width - pxHeight / 2, 0f),
                        pxHeight / 2
                    ),
                    strokeWidth = pxHeight,
                    cap = insideCap
                )
            }

        }
    }
}

@Preview
@Composable
fun LinearProgressBarPreview() {
    LinearProgressBar(
        progress = listOf(
            ProgressData(.3f, Color.Red),
            ProgressData(.7f, Color.Green),
        ),
        modifier = Modifier.width(200.dp),
        animationSpecs = AnimationSpecs(duration = 5000, betweenDelay = 1000),
        insideCap = StrokeCap.Butt
    ) {}
}