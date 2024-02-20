package ac.dnd.mour.android.presentation.common.view.chart.stick

import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Caption4
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.util.expansion.measureTextHeight
import ac.dnd.mour.android.presentation.common.util.expansion.measureTextWidth
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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun StickChart(
    modifier: Modifier = Modifier,
    dataList: List<StickChartData>,
    @FloatRange(from = 0.0, to = 1.0) thickness: Float
) {
    val topMeasuredList: List<Pair<Dp, Dp>> = dataList.map { data ->
        val formattedMoney = data.money.toString().let {
            if (it.length > 4) {
                "${it.substring(0, it.length - 4)}만원"
            } else {
                "${it}원"
            }
        }
        val topTextWidth = measureTextWidth(text = formattedMoney, style = Caption4)
        val topTextHeight = measureTextHeight(text = formattedMoney, style = Caption4)
        Pair(topTextWidth, topTextHeight)
    }
    val bottomMeasuredList: List<Pair<Dp, Dp>> = dataList.map { data ->
        val bottomTextWidth = measureTextWidth(text = data.text, style = Body2)
        val bottomTextHeight = measureTextHeight(text = data.text, style = Body2)
        Pair(bottomTextWidth, bottomTextHeight)
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
    ) {
        if (dataList.isNotEmpty()) {
            val fixedThickness = size.width / dataList.size * thickness
            val sum: Int = dataList.sumOf { it.money }
            val max: Int = dataList.maxOf { it.money }

            val topHeight = topMeasuredList.maxOf { it.second }.toPx() + 6.dp.toPx()
            val bottomHeight = bottomMeasuredList.maxOf { it.second }.toPx() + 24.dp.toPx()

            dataList.forEachIndexed { index, data ->
                val height = (size.height - topHeight - bottomHeight) * data.money / max
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
                        y = size.height - bottomHeight - height
                    ),
                    size = Size(
                        width = fixedThickness,
                        height = height
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
                drawRect(
                    brush = SolidColor(data.color),
                    topLeft = Offset(
                        x = centerX - fixedThickness / 2,
                        y = size.height - bottomHeight - height / 2
                    ),
                    size = Size(
                        width = fixedThickness,
                        height = height / 2
                    )
                )
                drawText(
                    textMeasurer = textMeasurer,
                    text = formattedMoney,
                    topLeft = Offset(
                        x = centerX - topMeasuredList[index].first.toPx() / 2,
                        y = size.height - height - topHeight - bottomHeight
                    ),
                    style = Caption4.merge(Gray700)
                )
                drawText(
                    textMeasurer = textMeasurer,
                    text = data.text,
                    topLeft = Offset(
                        x = centerX - topMeasuredList[index].first.toPx() / 2,
                        y = size.height - 10.dp.toPx() - bottomMeasuredList[index].second.toPx()
                    ),
                    style = Body2.merge(Gray700)
                )
            }
            drawLine(
                color = Gray500,
                start = Offset(
                    x = 0f,
                    y = size.height - bottomHeight
                ),
                end = Offset(
                    x = size.width,
                    y = size.height - bottomHeight
                ),
                strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }
    }
}

@Preview
@Composable
private fun StickChartPreview1() {
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
                    money = 10000,
                    text = "여성"
                ),
                StickChartData(
                    color = Color.Blue,
                    money = 20000,
                    text = "남성"
                )
            ),
            thickness = 0.5f
        )
    }
}

@Preview
@Composable
private fun StickChartPreview2() {
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
                    color = Color(0xFFFF547D),
                    money = Random.nextInt(50_000, 200_000),
                    text = "축의금"
                ),
                StickChartData(
                    color = Color(0xFFFF9461),
                    money = Random.nextInt(50_000, 400_000),
                    text = "생일"
                ),
                StickChartData(
                    color = Color(0xFFFFC524),
                    money = Random.nextInt(0, 50_000),
                    text = "출산"
                ),
                StickChartData(
                    color = Color(0xFF28A7F8),
                    money = Random.nextInt(0, 50_000),
                    text = "돌잔치"
                ),
                StickChartData(
                    color = Color(0xFF0DC9BA),
                    money = Random.nextInt(40_000, 300_000),
                    text = "개업"
                )
            ),
            thickness = 0.5f
        )
    }
}

@Preview
@Composable
private fun StickChartPreview3() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        StickChart(
            modifier = Modifier
                .fillMaxSize(),
            dataList = emptyList(),
            thickness = 0.5f
        )
    }
}
