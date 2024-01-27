package ac.dnd.bookkeeping.android.presentation.ui.main.login.main

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.HomeConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun LoginMainScreen(
    appState: ApplicationState,
    model: LoginMainModel,
    event: EventFlow<LoginMainEvent>,
    intent: (LoginMainIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    fun navigateToLogin() {
        appState.navController.navigate(HomeConstant.ROUTE) {
            popUpTo(LoginConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun login(event: LoginMainEvent.Login) {
        when (event) {
            LoginMainEvent.Login.Success -> {
                // TODO : Implement Success Case
            }

            is LoginMainEvent.Login.Error -> {
                // TODO : Implement Request Error Case
            }

            is LoginMainEvent.Login.Failure -> {
                // TODO : Implement Internal Server Error Case
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "서비스 이름",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(0xFF474747),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 157.16.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = 77.dp,
                    start = 18.dp,
                    end = 18.dp
                )
                .fillMaxWidth()
                .clickable {
                    navigateToLogin()
                }
                .clip(RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SampleComponent()
            Spacer(Modifier.height(20.23.dp))
            Image(
                painter = painterResource(R.drawable.ic_kakao_login_button),
                contentDescription = null
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is LoginMainEvent.Login -> login(event)
            }
        }
    }
}

@Composable
private fun SampleComponent(){
    Box(
        modifier = Modifier
            .width(115.96.dp)
            .height(32.47.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(16.23.dp)
            )
            .background(
                shape = RoundedCornerShape(16.23.dp),
                color = Color.White
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "3초만에 시작하기",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.2.sp,
            textAlign = TextAlign.Center
        )
    }
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
