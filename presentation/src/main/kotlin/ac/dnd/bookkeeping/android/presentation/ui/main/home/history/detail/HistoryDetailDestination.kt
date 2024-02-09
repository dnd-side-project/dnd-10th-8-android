package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.historyDetailDestination(
    appState: ApplicationState
) {
    composable(
        route = HistoryDetailConstant.CONTAIN_ID_MODEL,
        arguments = listOf(
            navArgument(HistoryDetailConstant.ROUTE_ARGUMENT_ID) {
                type = NavType.LongType
            }
        )
    ) { entry ->

        val id = entry.arguments?.getLong(HistoryDetailConstant.ROUTE_ARGUMENT_ID) ?: -1

        val viewModel: HistoryDetailViewModel = hiltViewModel()

        if (id < 0L) {
            appState.navController.popBackStack()
        } else {
            LaunchedEffect(Unit) {
                viewModel.loadRelationDetail(id)
            }
        }

        val model: HistoryDetailModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val relationDetail by viewModel.relationDetail.collectAsStateWithLifecycle()
            val heartList by viewModel.hearts.collectAsStateWithLifecycle()
            HistoryDetailModel(
                state = state,
                relationDetail = relationDetail,
                hearts = heartList
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
