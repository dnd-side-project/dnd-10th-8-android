package ac.dnd.mour.android.presentation.ui.main.home

import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.common.notification.notificationDestination
import ac.dnd.mour.android.presentation.ui.main.home.common.relation.relationDestination
import ac.dnd.mour.android.presentation.ui.main.home.history.historyDestination
import ac.dnd.mour.android.presentation.ui.main.home.mypage.myPageDestination
import ac.dnd.mour.android.presentation.ui.main.home.schedule.scheduleDestination
import ac.dnd.mour.android.presentation.ui.main.home.statistics.statisticsDestination
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.homeDestination(
    appState: ApplicationState
) {
    composable(
        route = HomeConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(HomeConstant.ROUTE_ARGUMENT_MESSAGE) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) { entry ->

        val message = entry.savedStateHandle.getStateFlow(
            HomeConstant.ROUTE_ARGUMENT_MESSAGE,
            initialValue = ""
        ).collectAsState()

        val viewModel: HomeViewModel = hiltViewModel()
        if(message.value.isNotEmpty()){
            LaunchedEffect(Unit) {
                viewModel.viewMessage(message.value)
                entry.savedStateHandle.remove<String>(HomeConstant.ROUTE_ARGUMENT_MESSAGE)
            }
        }

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
