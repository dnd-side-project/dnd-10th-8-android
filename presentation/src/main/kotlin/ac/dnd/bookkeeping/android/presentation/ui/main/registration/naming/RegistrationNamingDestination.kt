package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.RegistrationConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.RegistrationViewModel
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.registrationNamingDestination(
    appState: ApplicationState
) {
    composable(
        route = RegistrationNamingConstant.ROUTE
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry){
            appState.navController.getBackStackEntry(RegistrationConstant.ROUTE)
        }
        val parentViewModel: RegistrationViewModel = hiltViewModel(parentEntry)
        val viewModel: RegistrationNamingViewModel = hiltViewModel()

    }
}
