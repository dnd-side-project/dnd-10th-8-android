package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineExceptionHandler

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginOnBoardingScreen(
    appState: ApplicationState,
    model: LoginOnBoardingModel,
    event: EventFlow<LoginOnBoardingEvent>,
    intent: (LoginOnBoardingIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {

    val pagerState = rememberPagerState(pageCount = { 3 })

    fun navigateToHome() {
        appState.navController.navigate(HomeConstant.ROUTE) {
            popUpTo(LoginConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 75.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                SampleImageComponent(page.toString())
            }
            Spacer(Modifier.height(20.dp))
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color by animateColorAsState(
                        targetValue =
                        if (pagerState.currentPage == iteration) Color.LightGray
                        else Color.DarkGray,
                        label = "iteration color"
                    )
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(6.dp)
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = 18.dp,
                    vertical = 28.dp
                )
                .fillMaxWidth()
                .clickable {
                    intent(LoginOnBoardingIntent.OnClickNextStep)
                },
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFE0E0E0)
        ) {
            Text(
                text = "다음",
                fontSize = 16.sp,
                color = Color(0xFF636363),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(11.5.dp)
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is LoginOnBoardingEvent.GoToNextStep -> {}
                else -> {}
            }
        }
    }
}

@Composable
fun SampleImageComponent(contentIndex: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(442.65.dp)
            .padding(horizontal = 31.25.dp)
            .background(color = Color(0xFFEFEFEF)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = contentIndex,
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
