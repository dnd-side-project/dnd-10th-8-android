package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.HistoryMainModel
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.HistoryMainScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HistoryScreen(
    appState: ApplicationState,
    viewModel: HistoryViewModel = hiltViewModel()
) {

    val model: HistoryMainModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val historyInfo by viewModel.historyInfo.collectAsStateWithLifecycle()
        HistoryMainModel(
            state = state,
            historyInfo = historyInfo
        )
    }

    HistoryMainScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}
