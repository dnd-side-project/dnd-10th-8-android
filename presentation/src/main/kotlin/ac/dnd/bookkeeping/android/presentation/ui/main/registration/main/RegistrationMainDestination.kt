package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.RegistrationConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.RegistrationViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.registrationNamingDestination(
    appState: ApplicationState
) {
    composable(
        route = RegistrationMainConstant.ROUTE
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            appState.navController.getBackStackEntry(RegistrationConstant.ROUTE)
        }
        val parentViewModel: RegistrationViewModel = hiltViewModel(parentEntry)
        val viewModel: RegistrationMainViewModel = hiltViewModel()

        val model: RegistrationMainModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val errorType by viewModel.errorType.collectAsStateWithLifecycle()
            RegistrationMainModel(
                state = state,
                errorType = errorType
            )
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
