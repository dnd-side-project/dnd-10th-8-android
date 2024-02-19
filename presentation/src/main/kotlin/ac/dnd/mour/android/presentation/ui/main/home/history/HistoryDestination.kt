package ac.dnd.mour.android.presentation.ui.main.home.history

import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.history.detail.growth.historyDetailGrowthDestination
import ac.dnd.mour.android.presentation.ui.main.home.history.detail.historyDetailDestination
import ac.dnd.mour.android.presentation.ui.main.home.history.registration.historyRegistrationDestination
import ac.dnd.mour.android.presentation.ui.main.home.history.unrecorded.historyUnrecordedDestination
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.historyDestination(
    appState: ApplicationState
) {
    historyDetailDestination(appState)
    historyRegistrationDestination(appState)
    historyDetailGrowthDestination(appState)
    historyUnrecordedDestination(appState)
}
