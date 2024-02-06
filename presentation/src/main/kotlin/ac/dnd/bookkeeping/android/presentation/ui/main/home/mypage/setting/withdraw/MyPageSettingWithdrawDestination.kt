package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.setting.withdraw

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.myPageSettingWithdrawDestination(
    appState: ApplicationState
) {
    composable(
        route = MyPageSettingWithdrawConstant.ROUTE
    ) {
        val viewModel: MyPageSettingWithdrawViewModel = hiltViewModel()

        val model: MyPageSettingWithdrawModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            MyPageSettingWithdrawModel(
                state = state
            )
        }

        ErrorObserver(viewModel)

        MyPageSettingWithdrawScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
