package ac.dnd.mour.android.presentation.ui.main.login.onboarding

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray100
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.model.login.KakaoUserInformationModel
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.registration.main.RegistrationMainConstant
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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

    fun navigateToRegistration(kakaoUserModel: KakaoUserInformationModel) {
        appState.navController.sendKakaoUserModel(kakaoUserModel)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        HorizontalPager(
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> LoginOnBoardingPage(LoginOnBoardingViewType.FIRST)
                1 -> LoginOnBoardingPage(LoginOnBoardingViewType.SECOND)
                2 -> LoginOnBoardingPage(LoginOnBoardingViewType.THIRD)
                else -> LoginOnBoardingPage(LoginOnBoardingViewType.FIRST)
            }
        }

        Row(
            Modifier
                .wrapContentSize()
                .align(Alignment.BottomCenter)
                .padding(bottom = 99.55.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            repeat(pagerState.pageCount) { index ->
                val isSelected = pagerState.currentPage == index

                val color by animateColorAsState(
                    targetValue = if (isSelected) Primary4 else Color(0xFFD9D9D9),
                    label = "iteration color"
                )

                Box(
                    modifier = Modifier
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

        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray000)
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),
            onClick = {
                intent(LoginOnBoardingIntent.Click)
            }
        ) { style ->
            Text(
                text = stringResource(R.string.next_button_text),
                style = style
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is LoginOnBoardingEvent.Submit -> {
                    navigateToRegistration(event.kakaoUserModel)
                }
            }
        }
    }
}

@Composable
private fun LoginOnBoardingPage(
    loginOnBoardingViewType: LoginOnBoardingViewType
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = when (loginOnBoardingViewType) {
                LoginOnBoardingViewType.FIRST -> painterResource(R.drawable.bc_onboarding_1)
                LoginOnBoardingViewType.SECOND -> painterResource(R.drawable.bc_onboarding_2)
                LoginOnBoardingViewType.THIRD -> painterResource(R.drawable.bc_onboarding_3)
            },
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

    }
}

private fun NavHostController.sendKakaoUserModel(kakaoUserModel: KakaoUserInformationModel) {
    currentBackStackEntry?.savedStateHandle?.apply {
        set(
            key = RegistrationMainConstant.ROURE_ARGUMENT_USER_MODEL,
            value = kakaoUserModel
        )
    }
    navigate(RegistrationMainConstant.CONTAIN_USER_MODEL)
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
