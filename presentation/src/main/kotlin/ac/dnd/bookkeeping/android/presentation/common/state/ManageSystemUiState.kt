package ac.dnd.bookkeeping.android.presentation.common.state

import ac.dnd.bookkeeping.android.presentation.common.root.MainRootConstant
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun ManageSystemUiState(
    appState: ApplicationState
) {
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {

        ScreenRootConstant.SPLASH -> {
            appState.systemUiController.isStatusBarVisible = false
            appState.bottomBarState.value = false
        }

        MainRootConstant.BOTTOM_FIRST,
        MainRootConstant.BOTTOM_SECOND,
        MainRootConstant.BOTTOM_THIRD,
        MainRootConstant.BOTTOM_FOURTH,
        MainRootConstant.BOTTOM_FIFTH -> {
            appState.systemUiController.isStatusBarVisible = false
            appState.bottomBarState.value = true
        }
    }

}
