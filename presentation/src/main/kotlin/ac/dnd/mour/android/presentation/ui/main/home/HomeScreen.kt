package ac.dnd.mour.android.presentation.ui.main.home

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Icon24
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.CustomSnackBarHost
import ac.dnd.mour.android.presentation.model.main.MainBottomBarItem
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.history.HistoryConstant
import ac.dnd.mour.android.presentation.ui.main.home.history.HistoryScreen
import ac.dnd.mour.android.presentation.ui.main.home.mypage.MyPageConstant
import ac.dnd.mour.android.presentation.ui.main.home.mypage.MyPageScreen
import ac.dnd.mour.android.presentation.ui.main.home.schedule.ScheduleConstant
import ac.dnd.mour.android.presentation.ui.main.home.schedule.ScheduleScreen
import ac.dnd.mour.android.presentation.ui.main.home.statistics.StatisticsConstant
import ac.dnd.mour.android.presentation.ui.main.home.statistics.StatisticsScreen
import ac.dnd.mour.android.presentation.ui.main.home.test.TestConstant
import ac.dnd.mour.android.presentation.ui.main.home.test.TestScreen
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    appState: ApplicationState,
    model: HomeModel,
    event: EventFlow<HomeEvent>,
    intent: (HomeIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()

    val bottomBarItemList: List<MainBottomBarItem> = listOf(
        MainBottomBarItem(
            route = HistoryConstant.ROUTE,
            name = "내역",
            iconSelectedRes = R.drawable.ic_history_selected,
            iconUnselectedRes = R.drawable.ic_history_unselected
        ),
        MainBottomBarItem(
            route = StatisticsConstant.ROUTE,
            name = "통계",
            iconSelectedRes = R.drawable.ic_statistics_selected,
            iconUnselectedRes = R.drawable.ic_statistics_unselected
        ),
        MainBottomBarItem(
            route = ScheduleConstant.ROUTE,
            name = "일정",
            iconSelectedRes = R.drawable.ic_schedule_selected,
            iconUnselectedRes = R.drawable.ic_schedule_unselected
        ),
        MainBottomBarItem(
            route = MyPageConstant.ROUTE,
            name = "마이페이지",
            iconSelectedRes = R.drawable.ic_mypage_selected,
            iconUnselectedRes = R.drawable.ic_mypage_unselected
        ),
        MainBottomBarItem(
            route = TestConstant.ROUTE,
            name = "Test Page",
            iconSelectedRes = R.drawable.ic_warning,
            iconUnselectedRes = R.drawable.ic_warning
        )
    )
    val scaffoldState = rememberScaffoldState()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        pageCount = { bottomBarItemList.size }
    )

    LaunchedEffect(selectedItem) {
        pagerState.animateScrollToPage(selectedItem)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = CustomSnackBarHost,
        bottomBar = {
            HomeBottomBarScreen(
                itemList = bottomBarItemList,
                selectedItem = selectedItem,
                onClick = {
                    selectedItem = it
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
            userScrollEnabled = false
        ) { page ->
            when (bottomBarItemList[page].route) {
                HistoryConstant.ROUTE -> {
                    HistoryScreen(
                        appState = appState
                    )
                }

                ScheduleConstant.ROUTE -> {
                    ScheduleScreen(
                        appState = appState
                    )
                }

                StatisticsConstant.ROUTE -> {
                    StatisticsScreen(
                        appState = appState
                    )
                }

                MyPageConstant.ROUTE -> {
                    MyPageScreen(
                        appState = appState
                    )
                }

                TestConstant.ROUTE -> {
                    TestScreen(
                        appState = appState
                    )
                }
            }
        }
    }

    fun showSnackBar(message: String) {
        scope.launch(handler) {
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is HomeEvent.ShowSnackBar -> {
                    showSnackBar(event.message)
                }
            }
        }
    }
}

@Composable
private fun HomeBottomBarScreen(
    itemList: List<MainBottomBarItem>,
    selectedItem: Int,
    onClick: (Int) -> Unit
) {
    BottomNavigation(
        elevation = 0.dp,
        backgroundColor = Gray000,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {
        itemList.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = index == selectedItem,
                icon = {
                    val icon =
                        if (index == selectedItem) item.iconSelectedRes else item.iconUnselectedRes
                    Icon(
                        modifier = Modifier.size(Icon24),
                        painter = painterResource(id = icon),
                        contentDescription = "bottom icon"
                    )
                },
                onClick = {
                    onClick(index)
                }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        appState = rememberApplicationState(),
        model = HomeModel(
            state = HomeState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
