package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.loginOnBoardingDestination(
    appState: ApplicationState
) {
    composable(
        route = LoginOnBoardingConstant.ROUTE
    ) {

        val viewModel: LoginOnBoardingViewModel = hiltViewModel()

        val model: LoginOnBoardingModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            LoginOnBoardingModel(state = state)
        }

        ErrorObserver(viewModel)

        LoginOnBoardingScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
