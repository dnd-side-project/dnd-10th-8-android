package ac.dnd.mour.android.presentation.common.view.chart.pie

import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray300
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray600
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    val fixedDataList = dataList.ifEmpty {
        emptyDataList
    }

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .align(Alignment.Center)
        ) {
            val fixedThickness = size.width / 2 * thickness
            val sum: Int = fixedDataList.sumOf { it.value }
            fixedDataList.forEachIndexed { index, data ->
                val startAngle = -90f + fixedDataList.take(index).sumOf { it.value } * 360f / sum
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

private val emptyDataList: List<PieChartData> = listOf(
    PieChartData(
        color = Gray600,
        value = 7
    ),
    PieChartData(
        color = Gray500,
        value = 5
    ),
    PieChartData(
        color = Gray400,
        value = 2
    ),
    PieChartData(
        color = Gray300,
        value = 1
    ),
    PieChartData(
        color = Gray200,
        value = 2
    ),
)

@Preview
@Composable
private fun PieChartPreview1() {
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

@Preview
@Composable
private fun PieChartPreview2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        PieChart(
            modifier = Modifier
                .fillMaxSize(),
            dataList = emptyList(),
            thickness = 0.5f
        )
    }
}
