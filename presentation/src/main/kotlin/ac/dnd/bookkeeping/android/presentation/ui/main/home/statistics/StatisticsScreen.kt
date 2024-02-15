package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.notification.NotificationConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.StatisticsMeScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user.StatisticsUserScreen
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@Composable
fun StatisticsScreen(
    appState: ApplicationState,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val model: StatisticsModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        StatisticsModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    StatisticsScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StatisticsScreen(
    appState: ApplicationState,
    model: StatisticsModel,
    event: EventFlow<StatisticsEvent>,
    intent: (StatisticsIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()
    val pages = listOf("나의 통계", "사용자 트렌드")
    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )

    fun navigateToNotification() {
        appState.navController.navigate(NotificationConstant.ROUTE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray000)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "통계",
                style = Headline1,
                modifier = Modifier.align(Alignment.Center)
            )
            Image(
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        navigateToNotification()
                    }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp),
                divider = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
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
                                    color = if (index == pagerState.currentPage) Gray700 else Gray500,
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
                    StatisticsMeScreen(
                        appState = appState
                    )
                }

                1 -> {
                    StatisticsUserScreen(
                        appState = appState
                    )
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    StatisticsScreen(
        appState = rememberApplicationState(),
        model = StatisticsModel(
            state = StatisticsState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
