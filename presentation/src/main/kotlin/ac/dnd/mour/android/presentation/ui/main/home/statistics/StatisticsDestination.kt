package ac.dnd.mour.android.presentation.ui.main.home.statistics

import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.statistics.me.event.statisticsMeEventDestination
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.statisticsDestination(
    appState: ApplicationState
) {
    statisticsMeEventDestination(appState)
}
