package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.historyDetailDestination(
    appState: ApplicationState
) {
    composable(
        route = HistoryDetailConstant.ROUTE
    ) {
        val viewModel: HistoryDetailViewModel = hiltViewModel()

        val model: HistoryDetailModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            HistoryDetailModel(
                state = state
            )
        }

        ErrorObserver(viewModel)

        HistoryDetailScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
