package ac.dnd.mour.android.presentation.ui.main.home.mypage.setting

import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.myPageSettingDestination(
    appState: ApplicationState
) {
    composable(
        route = MyPageSettingConstant.ROUTE
    ) {
        val viewModel: MyPageSettingViewModel = hiltViewModel()

        val model: MyPageSettingModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            MyPageSettingModel(
                state = state
            )
        }

        ErrorObserver(viewModel)

        MyPageSettingScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
