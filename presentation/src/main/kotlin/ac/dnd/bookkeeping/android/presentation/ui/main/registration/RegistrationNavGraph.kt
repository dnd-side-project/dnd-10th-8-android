package ac.dnd.bookkeeping.android.presentation.ui.main.registration

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.registrationNavGraph(
    appState: ApplicationState
) {
    navigation(
        startDestination = RegistrationConstant.ROUTE,
        route = RegistrationConstant.ROUTE
    ) {

    }
}
