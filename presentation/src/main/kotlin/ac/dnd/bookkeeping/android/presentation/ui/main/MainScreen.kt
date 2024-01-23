package ac.dnd.bookkeeping.android.presentation.ui.main

import ac.dnd.bookkeeping.android.presentation.common.theme.BookkeepingTheme
import ac.dnd.bookkeeping.android.presentation.common.util.makeRoute
import ac.dnd.bookkeeping.android.presentation.ui.main.home.homeDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.login.loginNavGraph
import ac.dnd.bookkeeping.android.presentation.ui.main.splash.SplashConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.splash.splashDestination
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    BookkeepingTheme {
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
            homeDestination(appState = appState)
            splashDestination(appState = appState)
        }
    }
}

@Preview
@Composable
fun PreviewMainUi() {
    MainScreen()
}
