package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main

import ac.dnd.bookkeeping.android.domain.model.history.HistoryInfo
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray100
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline0
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail.HistoryDetailScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail.type.HistoryViewType
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryMainScreen(
    appState: ApplicationState,
    model: HistoryMainModel,
    event: EventFlow<HistoryMainEvent>,
    intent: (HistoryMainIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val pages = listOf("전체", "받은 마음", "보낸 마음")
    val pagerState = rememberPagerState(
        pageCount = { 3 }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .background(Gray100)
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(Gray000)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "MUR",
                style = Headline0.merge(
                    color = Gray700,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Image(
                painter = getAlarmImage(model.historyInfo.unReadAlarm),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        //TODO go alarm view
                    }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray000)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Gray400)
            )
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(horizontal = 20.dp),
                divider = {
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Gray400)
                    )
                }
            ) {
                pages.forEachIndexed { index, pageText ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = pageText,
                                style = Headline3.merge(
                                    color = if (index == pagerState.currentPage) Gray700 else Gray400,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { pageIndex ->
            when (pageIndex) {
                0 -> {
                    HistoryDetailScreen(
                        viewType = HistoryViewType.TOTAL,
                        mainModel = model
                    )
                }

                1 -> {
                    HistoryDetailScreen(
                        viewType = HistoryViewType.TAKE,
                        mainModel = model
                    )
                }

                2 -> {
                    HistoryDetailScreen(
                        viewType = HistoryViewType.GIVE,
                        mainModel = model
                    )
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is HistoryMainEvent.GetHistoryInfoMain -> {

                }
            }
        }
    }
}

@Composable
fun getAlarmImage(alarmOnState: Boolean): Painter {
    return painterResource(if (alarmOnState) R.drawable.ic_notification_on else R.drawable.ic_notification)
}

@Composable
@Preview
fun HistoryScreenPreview() {
    HistoryMainScreen(
        appState = rememberApplicationState(),
        model = HistoryMainModel(
            state = HistoryMainState.Init,
            historyInfo = HistoryInfo(
                unReadAlarm = true,
                totalHeartCount = 30,
                unWrittenCount = 5
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
