package ac.dnd.mour.android.presentation.ui.main.home.statistics

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.common.notification.NotificationConstant
import ac.dnd.mour.android.presentation.ui.main.home.statistics.me.StatisticsMeScreen
import ac.dnd.mour.android.presentation.ui.main.home.statistics.user.StatisticsUserScreen
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Divider
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = {
                            navigateToNotification()
                        }
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = null
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Divider(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(1.dp),
                color = Gray400
            )
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent,
                contentColor = Gray800,
                modifier = Modifier.padding(horizontal = 20.dp),
                divider = { }
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
                                    color = if (index == pagerState.currentPage) Gray800 else Gray500,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    )
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
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
