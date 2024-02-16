package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.model.relation.RelationDetailGroupModel
import ac.dnd.bookkeeping.android.presentation.model.relation.RelationDetailWithUserInfoModel
import ac.dnd.bookkeeping.android.presentation.model.relation.RelationType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.relationDestination(
    appState: ApplicationState
) {
    val defaultModel = RelationDetailWithUserInfoModel(
        id = 0L,
        name = "",
        imageUrl = "",
        memo = "",
        group = RelationDetailGroupModel(
            id = -1L,
            name = ""
        ),
        giveMoney = 0L,
        takeMoney = 0L
    )

    composable(
        route = RelationConstant.ROUTE
    ) {
        val viewModel: RelationViewModel = hiltViewModel()

        val model: RelationModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val groups by viewModel.groups.collectAsStateWithLifecycle()

            RelationModel(
                state = state,
                groups = groups,
                relationDetail = defaultModel
            )
        }

        ErrorObserver(viewModel)

        RelationScreen(
            relationType = RelationType.ADD,
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }

    composable(
        route = RelationConstant.CONTAIN_RELATION
    ) {
        val relationModel = appState.navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<RelationDetailWithUserInfoModel>(RelationConstant.ROUTE_ARGUMENT_MODEL)
            ?: defaultModel

        if (relationModel.id == -1L) {
            appState.navController.popBackStack()
        }

        val viewModel: RelationViewModel = hiltViewModel()
        val model: RelationModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val groups by viewModel.groups.collectAsStateWithLifecycle()

            RelationModel(
                state = state,
                groups = groups,
                relationDetail = relationModel
            )
        }

        ErrorObserver(viewModel)

        RelationScreen(
            relationType = RelationType.EDIT,
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
