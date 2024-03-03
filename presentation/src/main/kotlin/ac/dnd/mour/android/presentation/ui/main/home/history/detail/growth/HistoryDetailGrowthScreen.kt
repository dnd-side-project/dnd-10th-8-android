package ac.dnd.mour.android.presentation.ui.main.home.history.detail.growth

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.model.history.HistoryDetailGrowthType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun HistoryDetailGrowthScreen(
    appState: ApplicationState,
    model: HistoryDetailGrowthModel,
    event: EventFlow<HistoryDetailGrowthEvent>,
    intent: (HistoryDetailGrowthIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Gray200)
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        appState.navController.popBackStack()
                    }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "성장 단계",
            style = Headline1.merge(
                color = Gray900,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "총 6단계",
            style = Body1.merge(
                color = Gray700,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(33.dp))
        HistoryDetailGrowthType.entries.forEach { type ->
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(102.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 7.dp)
                        .align(Alignment.Center)
                        .background(
                            color = Gray000,
                            shape = Shapes.large
                        )
                        .padding(20.dp)
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Gray200,
                                shape = RoundedCornerShape(6.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(type.iconImageResource),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = type.typeName,
                            fontWeight = FontWeight.SemiBold,
                            style = Headline3.merge(color = Gray900)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = HistoryDetailGrowthType.getTypeString(type),
                            fontWeight = FontWeight.Normal,
                            style = Body1.merge(color = Gray700),
                            letterSpacing = (-0.25).sp
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Lv.${type.level}",
                            modifier = Modifier.align(Alignment.BottomEnd),
                            style = Body2.merge(
                                color = Gray600,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                }

                if (model.currentType == type) {
                    Box(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .align(Alignment.TopEnd)
                            .background(
                                color = Gray800,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(
                                horizontal = 12.dp,
                                vertical = 5.dp
                            )
                    ) {
                        Text(
                            text = "현재 달성",
                            style = Body2.merge(
                                color = Gray000,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(61.dp))
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
fun HistoryDetailGrowthScreenPreview() {
    HistoryDetailGrowthScreen(
        appState = rememberApplicationState(),
        model = HistoryDetailGrowthModel(
            state = HistoryDetailGrowthState.Init,
            currentType = HistoryDetailGrowthType.LEVEL_SIX
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
