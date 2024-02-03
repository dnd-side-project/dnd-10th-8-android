package ac.dnd.bookkeeping.android.presentation.ui.main.registration

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.RegistrationMainConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.registrationMainDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.registrationNavGraph(
    appState: ApplicationState
) {
    navigation(
        startDestination = RegistrationMainConstant.ROUTE,
        route = RegistrationConstant.ROUTE
    ) {
        registrationMainDestination(appState)
    }
}
