package ac.dnd.bookkeeping.android.presentation.ui.main.login.main

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.loginMainDestination(
    appState: ApplicationState
) {
    composable(
        route = LoginMainConstant.ROUTE
    ) {
        LoginMainScreen(
            appState = appState
        )
    }
}
