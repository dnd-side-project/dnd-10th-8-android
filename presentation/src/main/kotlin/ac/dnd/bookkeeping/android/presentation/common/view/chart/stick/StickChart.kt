package ac.dnd.bookkeeping.android.presentation.common.view.chart.stick

import ac.dnd.bookkeeping.android.presentation.common.theme.Caption4
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.measureTextHeight
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.measureTextWidth
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StickChart(
    modifier: Modifier = Modifier,
    dataList: List<StickChartData>,
    @FloatRange(from = 0.0, to = 1.0) thickness: Float
) {
    val textWidth = measureTextWidth(text = "50만원", style = Caption4)
    val textHeight = measureTextHeight(text = "50만원", style = Caption4)
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
    ) {
        val fixedThickness = size.width / dataList.size * thickness
        val sum: Int = dataList.sumOf { it.money }
        val max: Int = dataList.maxOf { it.money }

        dataList.forEachIndexed { index, data ->
            val height = (size.height - textHeight.toPx() - 6.dp.toPx()) * data.money / max
            val centerX = size.width / dataList.size * (index + 0.5f)
            val formattedMoney = data.money.toString().let {
                if (it.length > 4) {
                    "${it.substring(0, it.length - 4)}만원"
                } else {
                    "${it}원"
                }
            }

            drawRoundRect(
                brush = SolidColor(data.color),
                topLeft = Offset(
                    x = centerX - fixedThickness / 2,
                    y = size.height - height
                ),
                size = Size(
                    width = fixedThickness,
                    height = height + 16.dp.toPx()
                ),
                cornerRadius = CornerRadius(8.dp.toPx())
            )
            drawText(
                textMeasurer = textMeasurer,
                text = formattedMoney,
                topLeft = Offset(
                    x = centerX - textWidth.toPx() / 2,
                    y = size.height - height - textHeight.toPx() - 6.dp.toPx()
                ),
                style = Caption4.merge(Gray700)
            )
        }
    }
}

@Preview
@Composable
private fun StickChartPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        StickChart(
            modifier = Modifier
                .fillMaxSize(),
            dataList = listOf(
                StickChartData(
                    color = Color.Red,
                    money = 10000
                ),
                StickChartData(
                    color = Color.Blue,
                    money = 20000
                )
            ),
            thickness = 0.5f
        )
    }
}
