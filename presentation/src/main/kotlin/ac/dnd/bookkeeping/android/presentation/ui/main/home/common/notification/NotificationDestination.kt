package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.notification

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.notificationDestination(
    appState: ApplicationState
) {
    composable(
        route = NotificationConstant.ROUTE
    ) {
        val viewModel: NotificationViewModel = hiltViewModel()

        val model: NotificationModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val alarmList by viewModel.alarmList.collectAsStateWithLifecycle()

            NotificationModel(
                state = state,
                alarmList = alarmList
            )
        }

        ErrorObserver(viewModel)

        NotificationScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
