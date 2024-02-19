package ac.dnd.mour.android.presentation.ui.main

import ac.dnd.mour.android.presentation.common.theme.MourTheme
import ac.dnd.mour.android.presentation.common.util.makeRoute
import ac.dnd.mour.android.presentation.ui.main.home.homeDestination
import ac.dnd.mour.android.presentation.ui.main.login.loginNavGraph
import ac.dnd.mour.android.presentation.ui.main.registration.registrationNavGraph
import ac.dnd.mour.android.presentation.ui.main.splash.SplashConstant
import ac.dnd.mour.android.presentation.ui.main.splash.splashDestination
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    MourTheme {
        val appState = rememberApplicationState()
        ManageSystemUiState(appState = appState)

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
