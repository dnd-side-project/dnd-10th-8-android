package ac.dnd.mour.android.presentation.ui.main.login.main

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body0
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.loginWithKakao
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import ac.dnd.mour.android.presentation.model.login.KakaoUserInformationModel
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.HomeConstant
import ac.dnd.mour.android.presentation.ui.main.login.LoginConstant
import ac.dnd.mour.android.presentation.ui.main.login.onboarding.LoginOnBoardingConstant
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun LoginMainScreen(
    appState: ApplicationState,
    model: LoginMainModel,
    event: EventFlow<LoginMainEvent>,
    intent: (LoginMainIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    var isDialogShowing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    fun navigateToOnBoarding(kakaoUserModel: KakaoUserInformationModel) {
        appState.navController.sendKakaoUserModel(kakaoUserModel)
    }

    fun navigateToHome() {
        appState.navController.navigate(HomeConstant.ROUTE) {
            popUpTo(LoginConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun login(event: LoginMainEvent.Login) {
        when (event) {
            is LoginMainEvent.Login.Success -> {
                navigateToHome()
            }

            is LoginMainEvent.Login.RequireRegister -> {
                navigateToOnBoarding(event.kakaoUserModel)
            }

            is LoginMainEvent.Login.Error -> {
                isDialogShowing = true
            }

            is LoginMainEvent.Login.Failure -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF6D1FE),
                        Color(0xFFF9E1FE),
                        Color(0xFFFFFFFF),
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.bc_login),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 99.36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "감사한 마음을 잊지 않도록",
                fontWeight = FontWeight.Normal,
                style = Body0.merge(color = Gray800)
            )
            Spacer(modifier = Modifier.height(12.64.dp))
            Image(
                painter = painterResource(R.drawable.ic_login_logo),
                contentDescription = null,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.87.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .wrapContentSize()
                    .height(39.dp)
            ) {
                Card(
                    backgroundColor = Gray000,
                    shape = RoundedCornerShape(100.dp),
                    elevation = 0.dp,
                    contentColor = Color.Transparent,
                    modifier = Modifier
                        .height(34.dp)
                        .align(Alignment.TopCenter)
                ) {
                    Box(
                        modifier = Modifier
                            .height(34.dp)
                            .padding(horizontal = 18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "3초만에 시작하기",
                            fontWeight = FontWeight.SemiBold,
                            style = Body2.merge(color = Gray800)
                        )
                    }
                }
                Image(
                    painter = painterResource(R.drawable.ic_polygon),
                    modifier = Modifier
                        .offset(y = (-1).dp)
                        .align(Alignment.BottomCenter),
                    colorFilter = ColorFilter.tint(Gray000),
                    contentDescription = null,
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = 34.87.dp,
                    start = 22.dp,
                    end = 14.dp
                )
                .clip(Shapes.medium)
                .fillMaxWidth()
                .height(47.dp)
                .background(
                    color = Color(0xFFFEE500),
                    shape = Shapes.medium
                )
                .clickable {
                    if (model.state == LoginMainState.Init) {
                        loginWithKakao(
                            context = context,
                            onSuccess = {
                                intent(LoginMainIntent.Click)
                            },
                            onFailure = {
                                isDialogShowing = true
                            }
                        )
                    }
                }
                .padding(horizontal = 17.dp),
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterStart),
                painter = painterResource(R.drawable.ic_kakao_logo),
                contentDescription = null
            )

            Text(
                text = "카카오로 로그인",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.pretendard)),
                color = Gray900,
                letterSpacing = (-0.25).sp,
                fontSize = 16.sp
            )
        }
    }

    if (isDialogShowing) {
        DialogScreen(
            message = stringResource(R.string.login_main_dialog_message),
            onDismissRequest = {
                isDialogShowing = false
            }
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is LoginMainEvent.Login -> login(event)
            }
        }
    }
}

private fun NavHostController.sendKakaoUserModel(kakaoUserModel: KakaoUserInformationModel) {
    currentBackStackEntry?.savedStateHandle?.apply {
        set(
            key = LoginOnBoardingConstant.ROURE_ARGUMENT_USER_MODEL,
            value = kakaoUserModel
        )
    }
    navigate(LoginOnBoardingConstant.CONTAIN_USER_MODEL)
}

@Preview
@Composable
fun LoginMainScreenPreview() {
    LoginMainScreen(
        appState = rememberApplicationState(),
        model = LoginMainModel(
            state = LoginMainState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
