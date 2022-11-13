package com.jojo.enhancedprogress

import androidx.compose.animation.core.AnimationConstants

/**
 * Class used to override different timers in progress indicators such as [startDelay], [betweenDelay], [duration]
 * <p>
 * @param startDelay Time before the first animation starts
 * @param betweenDelay Time between each progress
 * @param duration Duration for each progress
 */
data class AnimationSpecs(
    val startDelay: Int = 0,
    val betweenDelay: Int = 20,
    val duration: Int = AnimationConstants.DefaultDurationMillis
)