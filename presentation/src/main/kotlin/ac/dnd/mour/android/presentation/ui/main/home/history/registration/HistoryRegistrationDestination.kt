package ac.dnd.mour.android.presentation.ui.main.home.history.registration

import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.historyRegistrationDestination(
    appState: ApplicationState
) {
    composable(
        route = HistoryRegistrationConstant.CONTAIN_RELATION_MODEL,
        arguments = listOf(
            navArgument(HistoryRegistrationConstant.ROUTE_ARGUMENT_NAME) {
                type = NavType.StringType
                defaultValue = "이름 선택"
            },
            navArgument(HistoryRegistrationConstant.ROUTE_ARGUMENT_ID) {
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument(HistoryRegistrationConstant.ROUTE_ARGUMENT_IS_HOME){
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) { entry ->

        val id = entry.arguments?.getLong(HistoryRegistrationConstant.ROUTE_ARGUMENT_ID) ?: -1L
        val name = entry.arguments?.getString(HistoryRegistrationConstant.ROUTE_ARGUMENT_NAME) ?: ""
        val isHome = entry.arguments?.getBoolean(HistoryRegistrationConstant.ROUTE_ARGUMENT_IS_HOME)?: false
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
            id = id,
            name = name,
            isHome = isHome
        )
    }
}
