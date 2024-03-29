package ac.dnd.mour.android.presentation.common.view.chart.stick

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class StickChartData(
    val color: Color,
    val money: Int,
    val text: String
)
