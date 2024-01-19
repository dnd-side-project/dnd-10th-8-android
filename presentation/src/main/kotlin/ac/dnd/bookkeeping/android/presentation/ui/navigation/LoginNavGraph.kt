package ac.dnd.bookkeeping.android.presentation.ui.navigation

import ac.dnd.bookkeeping.android.presentation.common.root.LoginRootConstant
import ac.dnd.bookkeeping.android.presentation.common.root.RootEntryPoint
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import ac.dnd.bookkeeping.android.presentation.common.state.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.login.LoginScreen
import ac.dnd.bookkeeping.android.presentation.ui.login.OnBoardScreen
import ac.dnd.bookkeeping.android.presentation.ui.splash.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.loginNavGraph(
    appState: ApplicationState
) {

    navigation(
        startDestination = ScreenRootConstant.LOGIN_SPLASH,
        route = ScreenRootConstant.LOGIN_GRAPH
    ) {

        composable(route = ScreenRootConstant.LOGIN_SPLASH) {
            SplashScreen(
                appState = appState,
                rootEntryPoint = RootEntryPoint.LOGIN
            )
        }

        composable(LoginRootConstant.LOGIN_MAIN) {
            LoginScreen(appState = appState)
        }

        composable(LoginRootConstant.LOGIN_ONBOARD) {
            OnBoardScreen(appState = appState)
        }

    }

}
