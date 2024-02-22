package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.myPageProfileDestination(
    appState: ApplicationState
) {
    composable(
        route = MyPageProfileConstant.ROUTE
    ) {

        val viewModel: MyPageProfileViewModel = hiltViewModel()

        val model: MyPageProfileModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val profile by viewModel.profile.collectAsStateWithLifecycle()
            MyPageProfileModel(
                state = state,
                profile = profile
            )
        }

        ErrorObserver(viewModel)

        MyPageProfileScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
