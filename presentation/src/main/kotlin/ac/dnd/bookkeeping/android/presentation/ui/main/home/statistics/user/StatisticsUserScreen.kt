package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user

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
fun StatisticsUserScreen(
    appState: ApplicationState
) {

    val viewModel: StatisticsUserViewModel = hiltViewModel()

    val model: StatisticsUserModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        StatisticsUserModel(
            state = state,
        )
    }

    ErrorObserver(viewModel)

    StatisticsUserScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@Composable
private fun StatisticsUserScreen(
    appState: ApplicationState,
    model: StatisticsUserModel,
    event: EventFlow<StatisticsUserEvent>,
    intent: (StatisticsUserIntent) -> Unit,
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
private fun StatisticsUserScreenPreview() {
    StatisticsUserScreen(
        appState = rememberApplicationState(),
        model = StatisticsUserModel(
            state = StatisticsUserState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
