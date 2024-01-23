package ac.dnd.bookkeeping.android.presentation.ui.main.splash

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.splashDestination(
    appState: ApplicationState
) {
    composable(
        route = SplashConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT) {
                defaultValue = SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT_MAIN
            }
        )
    ) {
        SplashScreen(
            appState = appState
        )
    }
}
