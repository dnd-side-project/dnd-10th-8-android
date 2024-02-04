package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.model.login.KakaoUserInformationModel
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.registrationMainDestination(
    appState: ApplicationState
) {
    composable(
        route = RegistrationMainConstant.CONTAIN_USER_MODEL
    ) {

        val userModel = appState.navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<KakaoUserInformationModel>(RegistrationMainConstant.ROURE_ARGUMENT_USER_MODEL)
            ?: KakaoUserInformationModel(0L, "", "", "")

        val viewModel: RegistrationMainViewModel = hiltViewModel()

        if (userModel.email.isEmpty()) {
            appState.navController.popBackStack()
        } else {
            LaunchedEffect(Unit) {
                viewModel.initKakaoUserInfo(userModel)
            }
        }

        val model: RegistrationMainModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val namingErrorType by viewModel.namingErrorType.collectAsStateWithLifecycle()
            RegistrationMainModel(
                state = state,
                namingErrorType = namingErrorType
            )
        }

        ErrorObserver(viewModel)

        RegistrationNamingScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
