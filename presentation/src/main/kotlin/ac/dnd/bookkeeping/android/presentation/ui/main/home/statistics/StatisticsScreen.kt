package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun StatisticsScreen(
    appState: ApplicationState,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val model: StatisticsModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        StatisticsModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    StatisticsScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@Composable
private fun StatisticsScreen(
    appState: ApplicationState,
    model: StatisticsModel,
    event: EventFlow<StatisticsEvent>,
    intent: (StatisticsIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()

    Box {

    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    StatisticsScreen(
        appState = rememberApplicationState(),
        model = StatisticsModel(
            state = StatisticsState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
