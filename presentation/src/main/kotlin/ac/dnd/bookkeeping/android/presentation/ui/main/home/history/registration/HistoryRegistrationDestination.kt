package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.historyRegistrationDestination(
    appState: ApplicationState
) {
    composable(
        route = HistoryRegistrationConstant.ROUTE
    ) {
        val viewModel: HistoryRegistrationViewModel = hiltViewModel()

        val model: HistoryRegistrationModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            HistoryRegistrationModel(
                state = state
            )
        }

        ErrorObserver(viewModel)

        HistoryRegistrationScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler,
        )
    }
}
