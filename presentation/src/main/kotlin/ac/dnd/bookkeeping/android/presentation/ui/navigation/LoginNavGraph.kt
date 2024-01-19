package ac.dnd.bookkeeping.android.presentation.ui.navigation

import ac.dnd.bookkeeping.android.presentation.common.root.LoginRootConstant
import ac.dnd.bookkeeping.android.presentation.common.root.RootEntryPoint
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import ac.dnd.bookkeeping.android.presentation.common.state.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.login.LoginScreen
import ac.dnd.bookkeeping.android.presentation.ui.login.LoginViewModel
import ac.dnd.bookkeeping.android.presentation.ui.login.OnBoardScreen
import ac.dnd.bookkeeping.android.presentation.ui.splash.SplashScreen
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
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
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(ScreenRootConstant.LOGIN_GRAPH)
            }
            val loginViewModel: LoginViewModel = hiltViewModel(backStackEntry)
            LoginScreen(appState = appState, viewModel = loginViewModel)
        }

        composable(LoginRootConstant.LOGIN_ONBOARD) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(ScreenRootConstant.LOGIN_GRAPH)
            }
            val loginViewModel: LoginViewModel = hiltViewModel(backStackEntry)
            OnBoardScreen(appState = appState, viewModel = loginViewModel)
        }

    }
}
