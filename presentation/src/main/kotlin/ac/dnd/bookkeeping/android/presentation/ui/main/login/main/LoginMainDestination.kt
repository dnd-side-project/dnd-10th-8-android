package ac.dnd.bookkeeping.android.presentation.ui.main.login.main

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.loginMainDestination(
    appState: ApplicationState
) {
    composable(
        route = LoginMainConstant.ROUTE
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            appState.navController.getBackStackEntry(LoginConstant.ROUTE)
        }
        val parentViewModel: LoginViewModel = hiltViewModel(parentEntry)
        val viewModel: LoginMainViewModel = hiltViewModel()

        val model: LoginMainModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            LoginMainModel(
                state = state
            )
        }

        LoginMainScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
