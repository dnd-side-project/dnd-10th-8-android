package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.scheduleAddDestination
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.scheduleDestination(
    appState: ApplicationState
) {
    scheduleAddDestination(appState)
}
