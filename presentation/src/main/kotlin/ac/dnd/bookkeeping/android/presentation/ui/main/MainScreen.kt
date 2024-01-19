package ac.dnd.bookkeeping.android.presentation.ui.main

import ac.dnd.bookkeeping.android.presentation.common.theme.BookkeepingTheme
import ac.dnd.bookkeeping.android.presentation.common.util.makeRoute
import ac.dnd.bookkeeping.android.presentation.ui.main.home.homeDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.login.loginNavGraph
import ac.dnd.bookkeeping.android.presentation.ui.main.splash.SplashConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.splash.splashDestination
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost

@Composable
fun MainScreen() {
    BookkeepingTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
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
}

@Preview
@Composable
fun PreviewMainUi() {
    MainScreen()
}
