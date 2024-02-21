package ac.dnd.mour.android.presentation.ui.main.home.schedule.add.repeat

import ac.dnd.mour.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body0
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space8
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.BottomSheetScreen
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun ScheduleAddRepeatScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onResult: (AlarmRepeatType?) -> Unit,
    initialSelectedType: AlarmRepeatType?,
    viewModel: ScheduleAddRepeatViewModel = hiltViewModel()
) {
    val model: ScheduleAddRepeatModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        ScheduleAddRepeatModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    ScheduleAddRepeatScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler,
        initialSelectedType = initialSelectedType,
        onDismissRequest = onDismissRequest,
        onResult = onResult
    )
}

@Composable
private fun ScheduleAddRepeatScreen(
    appState: ApplicationState,
    model: ScheduleAddRepeatModel,
    event: EventFlow<ScheduleAddRepeatEvent>,
    intent: (ScheduleAddRepeatIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    initialSelectedType: AlarmRepeatType?,
    onDismissRequest: () -> Unit,
    onResult: (AlarmRepeatType?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val choiceList: List<Pair<AlarmRepeatType?, String>> = listOf(
        null to "반복 안 함",
        AlarmRepeatType.Month to "매월",
        AlarmRepeatType.Year to "매년"
    )
    var selectedType: AlarmRepeatType? by remember { mutableStateOf(initialSelectedType) }

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray000)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Space8)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onDismissRequest()
                        }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "반복",
                style = Headline2
            )
            Spacer(modifier = Modifier.height(28.dp))
            choiceList.forEachIndexed { index, (type, text) ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
                val isSelected = selectedType == type
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedType = type
                        },
                    backgroundColor = Gray200,
                    shape = Shapes.medium,
                    elevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = text,
                            modifier = Modifier.weight(1f),
                            style = if (isSelected) Headline3 else Body0
                        )
                        if (isSelected) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.ic_check_circle),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            ConfirmButton(
                modifier = Modifier.fillMaxWidth(),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                onClick = {
                    onResult(selectedType)
                    onDismissRequest()
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
private fun ScheduleAddRepeatScreenPreview() {
    ScheduleAddRepeatScreen(
        appState = rememberApplicationState(),
        model = ScheduleAddRepeatModel(state = ScheduleAddRepeatState.Init),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        initialSelectedType = null,
        onResult = { }
    )
}
