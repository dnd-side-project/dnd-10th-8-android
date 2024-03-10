package ac.dnd.mour.android.presentation.ui.main.splash

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.alarm.registerAlarm
import ac.dnd.mour.android.presentation.common.util.alarm.unregisterAlarm
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.HomeConstant
import ac.dnd.mour.android.presentation.ui.main.login.LoginConstant
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun SplashScreen(
    appState: ApplicationState,
    model: SplashModel,
    event: EventFlow<SplashEvent>,
    intent: (SplashIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    appState.setStatusBarColor(Color(0xFFE579FF))

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val compositionSplash by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = R.raw.splash)
    )

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
            is SplashEvent.Login.Success -> {
                unregisterAlarm(context, event.alarmList)
                registerAlarm(context, event.alarmList)
                navigateToHome()
            }

            is SplashEvent.Login.Failure -> {
                navigateToLogin()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFE579FF),
                        Color(0xFFDE56FF),
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = compositionSplash,
            iterations = LottieConstants.IterateForever
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
private fun SplashScreenPreview() {
    SplashScreen(
        appState = rememberApplicationState(),
        model = SplashModel(state = SplashState.Init),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
