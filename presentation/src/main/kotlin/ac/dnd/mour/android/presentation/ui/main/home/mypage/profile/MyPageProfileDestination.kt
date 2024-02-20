package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.model.mypage.ProfileModel
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.datetime.LocalDate

fun NavGraphBuilder.myPageProfileDestination(
    appState: ApplicationState
) {
    composable(
        route = MyPageProfileConstant.CONTAIN_USER_MODEL
    ) {

        val profileModel = appState.navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<ProfileModel>(MyPageProfileConstant.ROURE_ARGUMENT_USER_MODEL)
            ?: ProfileModel(
                id = 0L,
                email = "",
                profileImageUrl = "",
                name = "",
                nickname = "",
                gender = "",
                birth = LocalDate(2020, 1, 1)
            )

        val viewModel: MyPageProfileViewModel = hiltViewModel()

        val model: MyPageProfileModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            MyPageProfileModel(
                state = state,
                profile = profileModel
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
