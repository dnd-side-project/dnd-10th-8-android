package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.scheduleAddDestination(
    appState: ApplicationState
) {
    composable(
        route = ScheduleAddConstant.ROUTE
    ) {
        val viewModel: ScheduleAddViewModel = hiltViewModel()

        val model: ScheduleAddModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            ScheduleAddModel(
                state = state
            )
        }

        ErrorObserver(viewModel)

        ScheduleAddScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
