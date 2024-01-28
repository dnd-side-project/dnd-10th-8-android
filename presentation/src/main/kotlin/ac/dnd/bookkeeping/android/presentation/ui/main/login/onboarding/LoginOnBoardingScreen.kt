package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.presentation.R
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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

    var currentSelectedPage by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { 3 })


    LaunchedEffect(currentSelectedPage) {
        pagerState.animateScrollToPage(currentSelectedPage)
    }

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
                state = pagerState
            ) { page ->
                SampleImageComponent(page.toString())
            }
            Spacer(Modifier.height(29.18.dp))
            Row(
                Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                repeat(pagerState.pageCount) { iterationIndex ->
                    val color by animateColorAsState(
                        targetValue =
                        if (pagerState.currentPage == iterationIndex) Color.DarkGray
                        else Color.LightGray,
                        label = "iteration color"
                    )
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .clickable { currentSelectedPage = iterationIndex }
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
                .fillMaxWidth()
                .clickable {
                    intent(LoginOnBoardingIntent.OnClickNextStep)
                },
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFDD55FF)
        ) {
            Text(
                text = stringResource(R.string.next_button_text),
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(14.dp)
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is LoginOnBoardingEvent.GoToNextStep -> navigateToHome()
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
            state = LoginOnBoardingState.Loading
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
