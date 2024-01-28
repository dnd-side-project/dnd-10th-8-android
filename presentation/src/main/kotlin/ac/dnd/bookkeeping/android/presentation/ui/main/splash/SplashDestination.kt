package ac.dnd.bookkeeping.android.presentation.ui.main.splash

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.splashDestination(
    appState: ApplicationState
) {
    composable(
        route = SplashConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT) {
                defaultValue = SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT_MAIN
            }
        )
    ) {

        val viewModel: SplashViewModel = hiltViewModel()

        val model: SplashModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            SplashModel(state = state)
        }

        ErrorObserver(viewModel)
        SplashScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
