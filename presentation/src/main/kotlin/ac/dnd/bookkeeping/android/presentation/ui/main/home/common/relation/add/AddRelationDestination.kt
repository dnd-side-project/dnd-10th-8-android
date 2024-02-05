package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.add

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.AddRelationDestination(
    appState: ApplicationState
) {

    composable(
        route = AddRelationConstant.ROUTE
    ) {
        val viewModel: AddRelationViewModel = hiltViewModel()

        val model: AddRelationModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val groups by viewModel.groups.collectAsStateWithLifecycle()

            AddRelationModel(
                state = state,
                groups = groups
            )
        }

        ErrorObserver(viewModel)

        AddRelationScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }

    composable(
        route = AddRelationConstant.CONTAIN_RELATION
    ) {
        // TODO Edit relation
    }
}
