package ac.dnd.bookkeeping.android.presentation.ui.main.splash

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.HomeConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun SplashScreen(
    appState: ApplicationState,
    model: SplashModel,
    event: EventFlow<SplashEvent>,
    intent: (SplashIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    fun navigateToLogin() {
        appState.navController.navigate(LoginConstant.ROUTE) {
            popUpTo(SplashConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun navigateToHome() {
        appState.navController.navigate(HomeConstant.ROUTE) {
            popUpTo(SplashConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun login(event: SplashEvent.Login) {
        when (event) {
            SplashEvent.Login.Success -> {
                navigateToHome()
            }

            is SplashEvent.Login.Failure -> {
                navigateToLogin()
            }

            is SplashEvent.Login.Error -> {
                // TODO : Unknown Error (Client Error, Internal Server Error, ...)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = ""
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = Headline1
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is SplashEvent.Login -> login(event)
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        appState = rememberApplicationState(),
        model = SplashModel(state = SplashState.Init),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
