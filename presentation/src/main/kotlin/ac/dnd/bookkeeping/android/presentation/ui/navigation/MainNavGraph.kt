package ac.dnd.bookkeeping.android.presentation.ui.navigation

import ac.dnd.bookkeeping.android.presentation.common.root.RootEntryPoint
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import ac.dnd.bookkeeping.android.presentation.common.state.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.bottombar.BottomBarScreen
import ac.dnd.bookkeeping.android.presentation.ui.bottombar.bottomGraph
import ac.dnd.bookkeeping.android.presentation.ui.splash.SplashScreen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
                SplashScreen(appState,RootEntryPoint.MAIN)
            }
        }

    }

}
