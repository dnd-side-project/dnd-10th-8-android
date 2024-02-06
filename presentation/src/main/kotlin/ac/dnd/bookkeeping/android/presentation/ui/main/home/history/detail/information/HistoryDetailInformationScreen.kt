package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.information

import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun HistoryDetailInformationScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onResult: () -> Unit,
    viewModel: HistoryDetailInformationViewModel = hiltViewModel()
) {
    val model: HistoryDetailInformationModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        HistoryDetailInformationModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    HistoryDetailInformationScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler,
        onDismissRequest = onDismissRequest,
        onResult = onResult
    )
}

@Composable
private fun HistoryDetailInformationScreen(
    appState: ApplicationState,
    model: HistoryDetailInformationModel,
    event: EventFlow<HistoryDetailInformationEvent>,
    intent: (HistoryDetailInformationIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: () -> Unit,
) {
    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            ),
            dismissOnBackPress = false
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray200)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "",
                style = Headline2
            )
            Spacer(modifier = Modifier.height(10.dp))
            ConfirmButton(
                modifier = Modifier.fillMaxWidth(),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                onClick = {
                    onResult()
                }
            ) { style ->
                Text(
                    text = "확인",
                    style = style
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun HistoryDetailInformationScreenPreview() {
    HistoryDetailInformationScreen(
        appState = rememberApplicationState(),
        model = HistoryDetailInformationModel(state = HistoryDetailInformationState.Init),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onResult = {}
    )
}
