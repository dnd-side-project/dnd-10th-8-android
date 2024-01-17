package ac.dnd.bookkeeping.android.presentation.common.state

import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRoot
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun ManageSystemUiState(
    appState: ApplicationState
) {
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()

    when(navBackStackEntry?.destination?.route) {

        ScreenRoot.SPLASH -> {
            appState.systemUiController.isStatusBarVisible = false
            appState.bottomBarState.value = false
        }

        ScreenRoot.MAIN_GRAPH -> {
            appState.systemUiController.isStatusBarVisible = false
            appState.bottomBarState.value = true
        }
    }

}