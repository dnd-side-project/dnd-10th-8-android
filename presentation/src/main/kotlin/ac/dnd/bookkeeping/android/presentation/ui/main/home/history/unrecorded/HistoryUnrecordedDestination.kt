package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.unrecorded

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.historyUnrecordedDestination(
    appState: ApplicationState
) {
    composable(
        route = HistoryUnrecordedConstant.ROUTE
    ) {
        val viewModel: HistoryUnrecordedViewModel = hiltViewModel()

        val model: HistoryUnrecordedModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            HistoryUnrecordedModel(
                state = state
            )
        }

        ErrorObserver(viewModel)

        HistoryUnrecordedScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
