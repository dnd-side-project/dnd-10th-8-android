package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.HomeConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginOnBoardingScreen(
    appState: ApplicationState,
    model: LoginOnBoardingModel,
    event: EventFlow<LoginOnBoardingEvent>,
    intent: (LoginOnBoardingIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope() + handler
    val pagerState = rememberPagerState(
        pageCount = { 3 }
    )

    fun navigateToHome() {
        appState.navController.navigate(HomeConstant.ROUTE) {
            popUpTo(LoginConstant.ROUTE) {
                inclusive = true
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 16.82.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                LoginOnBoardingPage(page.toString())
            }

            Spacer(Modifier.height(29.18.dp))

            Row(
                Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                repeat(pagerState.pageCount) { index ->
                    val isSelected = pagerState.currentPage == index

                    val color by animateColorAsState(
                        targetValue = if (isSelected) Color.DarkGray else Color.LightGray,
                        label = "iteration color"
                    )

                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }

        ConfirmButton(
            text = stringResource(R.string.next_button_text),
            isMain = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(25.dp),
        ) {
            navigateToHome()
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun LoginOnBoardingPage(
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(442.65.dp)
            .padding(horizontal = 31.25.dp)
            .background(color = Color(0xFFEFEFEF)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black,
        )
    }
}

@Preview
@Composable
fun LoginOnBoardingScreenPreview() {
    LoginOnBoardingScreen(
        appState = rememberApplicationState(),
        model = LoginOnBoardingModel(
            state = LoginOnBoardingState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
