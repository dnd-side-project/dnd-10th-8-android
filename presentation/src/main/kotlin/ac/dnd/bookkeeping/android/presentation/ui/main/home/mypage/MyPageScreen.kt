package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage

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
fun MyPageScreen(
    appState: ApplicationState,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val model: MyPageModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        MyPageModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    MyPageScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@Composable
private fun MyPageScreen(
    appState: ApplicationState,
    model: MyPageModel,
    event: EventFlow<MyPageEvent>,
    intent: (MyPageIntent) -> Unit,
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
private fun MyPageScreenPreview() {
    MyPageScreen(
        appState = rememberApplicationState(),
        model = MyPageModel(
            state = MyPageState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
