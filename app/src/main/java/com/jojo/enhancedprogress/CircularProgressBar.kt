package com.jojo.enhancedprogress

import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBar(
    progress: List<ProgressData>,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colors.secondary,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {

}

@ExperimentalAnimationApi
@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colors.secondary,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {

    val currentPercentage = remember {
        Animatable(0f)
    }

    val percentageAnimation = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        percentageAnimation.animateTo(
            360f,
            tween(durationMillis = animDuration, delayMillis = animDelay)
        )
    }

    LaunchedEffect(key1 = percentage) {
        currentPercentage.animateTo(
            percentage,
            tween(durationMillis = animDuration, delayMillis = animDelay + 200)
        )
    }

    Box(
        modifier = Modifier.size(radius * 2f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawCircle(Color.White, alpha = .6f)
            drawArc(
                color = Color(0xFF707070),
                startAngle = -90f,
                sweepAngle = percentageAnimation.value,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round),
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedContent(
                targetState = (currentPercentage.value * number).toInt().toString(),
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()
                    }.using(SizeTransform(clip = true))
                }) { targetCount ->
                Text(
                    text = targetCount, color = Color.Black,
                    fontSize = fontSize, fontWeight = FontWeight.Bold
                )
            }
            Surface(
                modifier = Modifier
                    .width(32.dp)
                    .height(4.dp),
                color = Color(0xFF707070),
                shape = RoundedCornerShape(100)
            ) {}
            Text(
                text = number.toString(), color = Color.Black,
                fontSize = fontSize, fontWeight = FontWeight.Bold
            )
        }
    }
}
