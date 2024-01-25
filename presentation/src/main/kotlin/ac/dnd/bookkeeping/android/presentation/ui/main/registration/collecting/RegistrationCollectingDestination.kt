package ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.RegistrationConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.RegistrationViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.registrationCollectingDestination(
    appState: ApplicationState
) {
    composable(
        route = RegistrationCollectingConstant.ROUTE
    ) { backStackEntry ->

        val parentEntry = remember(backStackEntry) {
            appState.navController.getBackStackEntry(RegistrationConstant.ROUTE)
        }
        val parentViewModel: RegistrationViewModel = hiltViewModel(parentEntry)
        val viewModel: RegistrationCollectingViewModel = hiltViewModel()

        val model: RegistrationCollectingModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            RegistrationCollectingModel(state = state)
        }

        ErrorObserver(viewModel)

        RegistrationNamingScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
