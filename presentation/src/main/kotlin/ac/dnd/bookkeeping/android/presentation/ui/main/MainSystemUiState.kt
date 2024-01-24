package ac.dnd.bookkeeping.android.presentation.ui.main

import ac.dnd.bookkeeping.android.presentation.common.util.parseRoute
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun ManageSystemUiState(
    appState: ApplicationState
) {
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route?.let {
        parseRoute(it).first
    }.orEmpty()

    when (route) {
        else -> {
            appState.systemUiController.isStatusBarVisible = true
        }
    }
}
