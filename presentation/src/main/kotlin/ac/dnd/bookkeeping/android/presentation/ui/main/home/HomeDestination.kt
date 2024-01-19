package ac.dnd.bookkeeping.android.presentation.ui.main.home

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.homeDestination(
    appState: ApplicationState
) {
    composable(
        route = HomeConstant.ROUTE
    ) {
        HomeScreen(appState)
    }
}
