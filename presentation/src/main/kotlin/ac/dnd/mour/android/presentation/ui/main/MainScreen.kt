package ac.dnd.mour.android.presentation.ui.main

import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.MourTheme
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.makeRoute
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import ac.dnd.mour.android.presentation.ui.main.home.homeDestination
import ac.dnd.mour.android.presentation.ui.main.login.loginNavGraph
import ac.dnd.mour.android.presentation.ui.main.registration.registrationNavGraph
import ac.dnd.mour.android.presentation.ui.main.splash.SplashConstant
import ac.dnd.mour.android.presentation.ui.main.splash.splashDestination
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    MourTheme {
        val appState = rememberApplicationState()
        ManageSystemUiState(appState = appState)

        ErrorObserver(viewModel)
        MainScreenRefreshFailDialog(appState, viewModel.refreshFailEvent)

        NavHost(
            navController = appState.navController,
            startDestination = makeRoute(
                SplashConstant.ROUTE,
                listOf(SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT_MAIN to SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT_MAIN)
            )
        ) {
            loginNavGraph(appState = appState)
            registrationNavGraph(appState = appState)
            homeDestination(appState = appState)
            splashDestination(appState = appState)
        }
    }
}

@Composable
fun MainScreenRefreshFailDialog(
    appState: ApplicationState,
    refreshFailEvent: EventFlow<Unit>
) {
    var isInvalidTokenDialogShowing: Boolean by remember { mutableStateOf(false) }

    if (isInvalidTokenDialogShowing) {
        DialogScreen(
            isCancelable = false,
            title = stringResource(R.string.invalid_jwt_token_dialog_title),
            message = stringResource(R.string.invalid_jwt_token_dialog_content),
            onConfirm = {
                appState.navController.navigate(SplashConstant.ROUTE)
            },
            onDismissRequest = {
                isInvalidTokenDialogShowing = false
            }
        )
    }

    LaunchedEffectWithLifecycle(refreshFailEvent) {
        refreshFailEvent.eventObserve {
            isInvalidTokenDialogShowing = true
        }
    }
}
