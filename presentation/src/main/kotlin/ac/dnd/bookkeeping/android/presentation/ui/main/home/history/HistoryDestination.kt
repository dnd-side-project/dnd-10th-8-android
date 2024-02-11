package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.historyDetailDestination
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.historyDestination(
    appState: ApplicationState
) {
    historyDetailDestination(appState)
}
