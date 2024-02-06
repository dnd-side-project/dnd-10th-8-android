package ac.dnd.bookkeeping.android.presentation.ui.main.home

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Caption4
import ac.dnd.bookkeeping.android.presentation.common.theme.Icon24
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.CustomSnackBarHost
import ac.dnd.bookkeeping.android.presentation.model.main.MainBottomBarItem
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.HistoryConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.HistoryScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.MyPageConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.MyPageScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.StatisticsConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.StatisticsScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.test.TestConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.test.TestScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
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
import androidx.compose.material.Text
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
            icon = R.drawable.ic_launcher
        ),
//        MainBottomBarItem(
//            route = ScheduleConstant.ROUTE,
//            name = "통계",
//            icon = R.drawable.ic_launcher
//        ),
        MainBottomBarItem(
            route = StatisticsConstant.ROUTE,
            name = "일정",
            icon = R.drawable.ic_launcher
        ),
        MainBottomBarItem(
            route = MyPageConstant.ROUTE,
            name = "마이페이지",
            icon = R.drawable.ic_launcher
        ),
        MainBottomBarItem(
            route = TestConstant.ROUTE,
            name = "Test Page",
            icon = R.drawable.ic_warning
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
//                ScheduleConstant.ROUTE -> {
//
//                }
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
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        itemList.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = index == selectedItem,
                icon = {
                    Icon(
                        modifier = Modifier.size(Icon24),
                        painter = painterResource(id = item.icon),
                        contentDescription = "bottom icon"
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        style = Caption4
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
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
