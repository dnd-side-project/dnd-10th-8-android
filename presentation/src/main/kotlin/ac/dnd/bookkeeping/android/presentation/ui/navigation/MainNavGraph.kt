package ac.dnd.bookkeeping.android.presentation.ui.navigation

import ac.dnd.bookkeeping.android.presentation.ui.bottombar.BottomBarScreen
import ac.dnd.bookkeeping.android.presentation.ui.bottombar.bottomGraph
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import ac.dnd.bookkeeping.android.presentation.common.state.ApplicationState
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainNavGraph(
    appState: ApplicationState
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = appState.scaffoldState,
        bottomBar = { BottomBarScreen(appState = appState) }
    ) { innerPadding ->

        BackHandler(enabled = true, onBack = {})
        NavHost(
            navController = appState.navController,
            startDestination = ScreenRootConstant.SPLASH,
            modifier = Modifier.padding(innerPadding)
        ) {
            bottomGraph(appState)

            composable(route = ScreenRootConstant.SPLASH) {
                SplashScreen(appState)
            }
        }

    }

}

@Composable
fun SplashScreen(
    appState: ApplicationState
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            delay(500L)
            appState.navController.navigate(ScreenRootConstant.MAIN_GRAPH)
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
