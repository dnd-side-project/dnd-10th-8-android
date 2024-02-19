package ac.dnd.mour.android.presentation.ui.main.home.mypage.setting.withdraw

import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.myPageSettingWithdrawDestination(
    appState: ApplicationState
) {
    composable(
        route = MyPageSettingWithdrawConstant.CONTAIN_NAME_MODEL,
        arguments = listOf(
            navArgument(MyPageSettingWithdrawConstant.ROUTE_ARGUMENT_NAME) {
                type = NavType.StringType
            }
        )
    ) { entry ->

        val nickname =
            entry.arguments?.getString(MyPageSettingWithdrawConstant.ROUTE_ARGUMENT_NAME) ?: ""
        val viewModel: MyPageSettingWithdrawViewModel = hiltViewModel()

        val model: MyPageSettingWithdrawModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            MyPageSettingWithdrawModel(
                state = state,
                nickname = nickname
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
