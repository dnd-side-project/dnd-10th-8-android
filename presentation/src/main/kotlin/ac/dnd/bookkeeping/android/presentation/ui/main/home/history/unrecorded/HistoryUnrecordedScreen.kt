package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.unrecorded

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.HomeConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryUnrecordedScreen(
    appState: ApplicationState,
    model: HistoryUnrecordedModel,
    event: EventFlow<HistoryUnrecordedEvent>,
    intent: (HistoryUnrecordedIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {

    var currentPage by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        pageCount = { model.unrecordedList.size }
    )

    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }

    fun navigateToHome() {
        appState.navController.navigate(HomeConstant.ROUTE)
    }

    fun navigateToBack() {
        appState.navController.popBackStack()
    }

    fun resetData(event: HistoryUnrecordedEvent.ShowNextData) {
        when (event) {
            HistoryUnrecordedEvent.ShowNextData -> {
                if (currentPage < pagerState.pageCount - 1) {
                    currentPage += 1
                } else {
                    navigateToHome()
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Gray200)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .padding(horizontal = 20.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        navigateToBack()
                    }
            )
        }
        Spacer(modifier = Modifier.height(9.dp))
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            HistoryUnrecordedPageScreen(
                unRecordSize = model.unrecordedList.size - page,
                unrecordedSchedule = model.unrecordedList[page],
                intent = intent
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is HistoryUnrecordedEvent.ShowNextData -> resetData(event)
            }
        }
    }
}

@Preview
@Composable
fun HistoryUnrecordedScreenPreview() {
    HistoryUnrecordedScreen(
        appState = rememberApplicationState(),
        model = HistoryUnrecordedModel(
            state = HistoryUnrecordedState.Init,
            unrecordedList = listOf(),
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
