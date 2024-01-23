package ac.dnd.bookkeeping.android.presentation.ui.main.login

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.loginNavGraph(
    appState: ApplicationState
) {
    navigation(
        startDestination = LoginConstant.ROUTE_STEP_1,
        route = LoginConstant.ROUTE
    ) {
        // TODO : 분리
        composable(
            route = LoginConstant.ROUTE_STEP_1
        ) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(LoginConstant.ROUTE_STEP_1)
            }
            val loginViewModel: LoginViewModel = hiltViewModel(backStackEntry)
            LoginScreen(appState = appState, viewModel = loginViewModel)
        }

        // TODO : 분리
        composable(
            route = LoginConstant.ROUTE_STEP_2
        ) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(LoginConstant.ROUTE)
            }
            val loginViewModel: LoginViewModel = hiltViewModel(backStackEntry)
            OnBoardScreen(appState = appState, viewModel = loginViewModel)
        }
    }
}
