package ac.dnd.mour.android.presentation.ui.main.login

import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.login.main.LoginMainConstant
import ac.dnd.mour.android.presentation.ui.main.login.main.loginMainDestination
import ac.dnd.mour.android.presentation.ui.main.login.onboarding.loginOnBoardingDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.loginNavGraph(
    appState: ApplicationState
) {
    navigation(
        startDestination = LoginMainConstant.ROUTE,
        route = LoginConstant.ROUTE
    ) {
        loginMainDestination(appState = appState)
        loginOnBoardingDestination(appState = appState)
    }
}
