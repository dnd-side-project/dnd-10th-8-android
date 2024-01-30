package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.name

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

fun NavGraphBuilder.searchNameDestination(
    appState: ApplicationState
) {
    composable(
        route = SearchNameConstant.ROUTE
    ) {
        val viewModel: SearchNameViewModel = hiltViewModel()

        val model: SearchNameModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            SearchNameModel(state = state)
        }

        ErrorObserver(viewModel)

        SearchNameScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
