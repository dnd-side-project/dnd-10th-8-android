package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.unrecorded

import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun HistoryUnrecordedScreen(
    appState: ApplicationState,
    model: HistoryUnrecordedModel,
    event: EventFlow<HistoryUnrecordedEvent>,
    intent: (HistoryUnrecordedIntent) -> Unit,
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
fun HistoryUnrecordedScreenPreview() {
    HistoryUnrecordedScreen(
        appState = rememberApplicationState(),
        model = HistoryUnrecordedModel(
            state = HistoryUnrecordedState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
