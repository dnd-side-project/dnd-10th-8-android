package ac.dnd.bookkeeping.android.presentation.ui.main.splash

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.repeatOnStarted
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.HomeConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginConstant
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun SplashScreen(
    appState: ApplicationState,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Observer(
        appState = appState,
        viewModel = viewModel
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = ""
        )
        Text(
            text = "Splash Screen",
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}

@Composable
private fun Observer(
    appState: ApplicationState,
    viewModel: SplashViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current

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

            is SplashEvent.Login.Error -> {
                navigateToLogin()
            }

            is SplashEvent.Login.Failure -> {
                // TODO : Dialog Screen
            }
        }
    }
    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnStarted(CoroutineExceptionHandler(appState.coroutineExceptionHandler)) {
            viewModel.event.eventObserve { event ->
                when (event) {
                    is SplashEvent.Login -> login(event)
                }
            }
        }
    }
}
