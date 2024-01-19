package ac.dnd.bookkeeping.android.presentation.ui.main.splash

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginConstant
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    appState: ApplicationState,
    // TODO : savedStateHandle 사용
    arguments: Bundle,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val entryPoint = arguments.getString(SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT).orEmpty()
    LaunchedEffect(Unit) {
        scope.launch {
            delay(1000L)
            when (entryPoint) {
                SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT_MAIN -> {
                    appState.navController.navigate(LoginConstant.ROUTE) {
                        popUpTo(SplashConstant.ROUTE) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Splash Screen",
            fontSize = 20.sp,
            color = Color.White
        )
    }
}
