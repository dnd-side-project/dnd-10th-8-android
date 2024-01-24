package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.login.main.LoginMainConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginOnBoardingScreen(
    appState: ApplicationState,
    model: LoginOnBoardingModel,
    event: EventFlow<LoginOnBoardingEvent>,
    intent: (LoginOnBoardingIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {


    fun loading(event: LoginOnBoardingEvent.Loading) {
        when (event) {
            LoginOnBoardingEvent.Loading.Success -> {
                // TODO: loading success
            }

            is LoginOnBoardingEvent.Loading.Error -> {
                // TODO : Implement Request Error Case
            }

            is LoginOnBoardingEvent.Loading.Failure -> {
                // TODO : Implement Internal Server Error Case
            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
    ) {
        Text(
            text = "OnBoard Screen",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(30.dp)
                .fillMaxWidth()
                .clickable {
                    intent(LoginOnBoardingIntent.GoToNextStep)
                },
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            Text(
                text = "next",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp)
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.collectLatest { event ->
            when (event.value) {
                is LoginOnBoardingEvent.OnClickNextStep -> {
                    appState.navController.navigate(LoginMainConstant.ROUTE) {
                        popUpTo(LoginOnBoardingConstant.ROUTE) {
                            inclusive = true
                        }
                    }
                }
            }
        }
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
