package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.ScheduleAddConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler


@Composable
fun ScheduleScreen(
    appState: ApplicationState,
    viewModel: ScheduleViewModel = hiltViewModel()
) {

    val model: ScheduleModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        ScheduleModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    ScheduleScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@Composable
private fun ScheduleScreen(
    appState: ApplicationState,
    model: ScheduleModel,
    event: EventFlow<ScheduleEvent>,
    intent: (ScheduleIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()

    fun navigateToScheduleAdd() {
        appState.navController.navigate(ScheduleAddConstant.ROUTE)
    }

    Box {
        Text(
            text = "ScheduleAdd",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    navigateToScheduleAdd()
                }
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    ScheduleScreen(
        appState = rememberApplicationState(),
        model = ScheduleModel(
            state = ScheduleState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
