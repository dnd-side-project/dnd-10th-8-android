package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.growth

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryDetailGrowthType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.historyDetailGrowthDestination(
    appState: ApplicationState
) {
    composable(
        route = HistoryDetailGrowthConstant.CONTAIN_MONEY_MODEL,
        arguments = listOf(
            navArgument(HistoryDetailGrowthConstant.ROUTE_ARGUMENT_TOTAL_MONEY) {
                type = NavType.LongType
            }
        )
    ) { entry ->

        val totalMoney = entry.arguments?.getLong(HistoryDetailGrowthConstant.ROUTE_ARGUMENT_TOTAL_MONEY) ?: -1L
        if (totalMoney < 0){
            appState.navController.popBackStack()
        }

        val viewModel: HistoryDetailGrowthViewModel = hiltViewModel()

        val model: HistoryDetailGrowthModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            HistoryDetailGrowthModel(
                currentType = HistoryDetailGrowthType.getGrowthType(totalMoney),
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
