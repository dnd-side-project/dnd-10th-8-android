package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.scheduleDestination(
    appState: ApplicationState
) {
    scheduleAddDestination(appState)
}
