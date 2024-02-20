package ac.dnd.mour.android.presentation.ui.main

import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.util.parseRoute
import ac.dnd.mour.android.presentation.ui.main.home.HomeConstant
import ac.dnd.mour.android.presentation.ui.main.home.schedule.add.ScheduleAddConstant
import androidx.compose.material.MaterialTheme
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
        HomeConstant.ROUTE,
        ScheduleAddConstant.ROUTE -> {
            appState.systemUiController.setSystemBarsColor(Gray000)
        }

        else -> {
            appState.systemUiController.setSystemBarsColor(MaterialTheme.colors.background)
            appState.systemUiController.isStatusBarVisible = true
        }
    }
}
