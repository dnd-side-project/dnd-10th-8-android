package ac.dnd.bookkeeping.android.presentation.ui.main.registration

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting.registrationCollectingDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming.RegistrationNamingConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming.registrationNamingDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.registrationNavGraph(
    appState: ApplicationState
) {
    navigation(
        startDestination = RegistrationNamingConstant.ROUTE,
        route = RegistrationConstant.ROUTE
    ) {
        registrationNamingDestination(appState)
        registrationCollectingDestination(appState)
    }
}
