package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.HistoryMainConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.historyMainDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.historyNavGraph(
    appState: ApplicationState
) {
    navigation(
        startDestination = HistoryMainConstant.ROUTE,
        route = HistoryConstant.ROUTE
    ){
        historyMainDestination(appState)
    }
}
