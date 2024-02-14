package ac.dnd.bookkeeping.android.presentation.common.view.chart.pie

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PieChartData(
    val color: Color,
    val value: Int
)
