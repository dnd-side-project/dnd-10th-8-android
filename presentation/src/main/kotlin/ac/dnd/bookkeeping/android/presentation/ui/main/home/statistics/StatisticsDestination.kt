package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.scheduleAddDestination
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.event.statisticsMeEventDestination
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.statisticsDestination(
    appState: ApplicationState
) {
    statisticsMeEventDestination(appState)
}
