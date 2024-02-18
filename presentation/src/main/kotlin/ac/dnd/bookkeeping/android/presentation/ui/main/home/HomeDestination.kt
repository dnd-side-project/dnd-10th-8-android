package ac.dnd.bookkeeping.android.presentation.ui.main.home

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.notification.notificationDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.relationDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.historyDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.myPageDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.scheduleDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.statisticsDestination
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.homeDestination(
    appState: ApplicationState
) {
    composable(
        route = HomeConstant.ROUTE
    ) {
        val viewModel: HomeViewModel = hiltViewModel()

        val model: HomeModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            HomeModel(
                state = state
            )
        }

        ErrorObserver(viewModel)

        HomeScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }

    historyDestination(appState)
    relationDestination(appState)
    scheduleDestination(appState)
    statisticsDestination(appState)
    myPageDestination(appState)

    notificationDestination(appState)
}
