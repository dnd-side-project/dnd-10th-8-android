package ac.dnd.bookkeeping.android.presentation.common.view.chart.pie

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    dataList: List<PieChartData>,
    @FloatRange(from = 0.0, to = 1.0) thickness: Float
) {
    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = modifier.aspectRatio(1f)
        ) {
            val fixedThickness = size.width / 2 * thickness
            val sum: Int = dataList.sumOf { it.value }
            dataList.forEachIndexed { index, data ->
                val startAngle = dataList.take(index).sumOf { it.value } * 360f / sum
                val angle = data.value * 360f / sum
                drawArc(
                    color = data.color,
                    startAngle = startAngle,
                    sweepAngle = angle,
                    useCenter = false,
                    topLeft = Offset(
                        x = fixedThickness / 2,
                        y = fixedThickness / 2
                    ),
                    size = Size(
                        size.width - fixedThickness,
                        size.height - fixedThickness
                    ),
                    style = Stroke(
                        width = fixedThickness
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun PieChartPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        PieChart(
            modifier = Modifier
                .fillMaxSize(),
            dataList = listOf(
                PieChartData(
                    color = Color.Red,
                    value = 1
                ),
                PieChartData(
                    color = Color.Blue,
                    value = 2
                )
            ),
            thickness = 0.5f
        )
    }
}
