package ac.dnd.bookkeeping.android.presentation.common.state

import ac.dnd.bookkeeping.android.presentation.common.root.LoginRootConstant
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

        ScreenRootConstant.LOGIN_SPLASH, ScreenRootConstant.MAIN_SPLASH -> {
            appState.systemUiController.isStatusBarVisible = false
            appState.systemUiController.isNavigationBarVisible = false
            appState.bottomBarState.value = false
        }

        MainRootConstant.BOTTOM_FIRST,
        MainRootConstant.BOTTOM_SECOND,
        MainRootConstant.BOTTOM_THIRD,
        MainRootConstant.BOTTOM_FOURTH,
        MainRootConstant.BOTTOM_FIFTH -> {
            appState.systemUiController.isStatusBarVisible = false
            appState.systemUiController.isNavigationBarVisible = true
            appState.bottomBarState.value = true
        }

        LoginRootConstant.LOGIN_ONBOARD, LoginRootConstant.LOGIN_MAIN -> {
            appState.systemUiController.isStatusBarVisible = true
            appState.systemUiController.isNavigationBarVisible = true
            appState.bottomBarState.value = false
        }
    }

}
