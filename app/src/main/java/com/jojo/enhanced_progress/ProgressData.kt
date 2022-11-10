package com.jojo.enhanced_progress

import androidx.compose.ui.graphics.Color

/**
 * Class used for different enhanced progress indicators to define a progress to be shown in with a color
 * <a>
 * @param progress Value between 0f-1f (both included) defining a part of a progress indicators
 * @param color The color which will be displayed on the progress indicator for the part
 */
data class ProgressData(
    val progress: Float,
    val color: Color
)