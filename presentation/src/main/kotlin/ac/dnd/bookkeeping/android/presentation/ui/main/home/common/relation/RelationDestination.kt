package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.RelationDestination(
    appState: ApplicationState
) {

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
                relationDetail = RelationDetailWithUserInfo(
                    id = 0L,
                    name = "",
                    imageUrl = "",
                    memo = "",
                    group =  RelationDetailGroup(
                        id = -1L,
                        name = ""
                    ),
                    giveMoney = 0L,
                    takeMoney = 0L
                )
            )
        }

        ErrorObserver(viewModel)

        RelationScreen(
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
        // TODO Edit relation
    }
}
