package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.growth

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.historyDetailGrowthDestination(
    appState: ApplicationState
) {
    composable(
        route = HistoryDetailGrowthConstant.ROUTE
    ) {
        val viewModel: HistoryDetailGrowthViewModel = hiltViewModel()

        val model: HistoryDetailGrowthModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            HistoryDetailGrowthModel(
                state = state
            )
        }

        ErrorObserver(viewModel)

        HistoryDetailGrowthScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
