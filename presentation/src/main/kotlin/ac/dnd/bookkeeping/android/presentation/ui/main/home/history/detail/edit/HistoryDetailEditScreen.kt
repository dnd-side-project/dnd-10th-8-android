package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.edit

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space24
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipItem
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipType
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSelectComponent
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSubject
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingPriceField
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryRegistrationTagType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun HistoryDetailEditScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onResult: () -> Unit,
    viewModel: HistoryDetailEditViewModel = hiltViewModel()
) {
    val model: HistoryDetailEditModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        HistoryDetailEditModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    HistoryDetailEditScreen(
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
private fun HistoryDetailEditScreen(
    appState: ApplicationState,
    model: HistoryDetailEditModel,
    event: EventFlow<HistoryDetailEditEvent>,
    intent: (HistoryDetailEditIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var moneyText by remember { mutableStateOf("") }
    var dateText by remember{ mutableStateOf("") }
    var eventText by remember { mutableStateOf("") }

    var isEventSelected by remember { mutableStateOf(false) }
    var isMemoSelected by remember{ mutableStateOf(false) }
    var isCalendarSelected by remember { mutableStateOf(false) }

    val tagIdList = remember { mutableStateListOf<Long>() }
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
                .padding(horizontal = Space20),
        ) {
            Spacer(modifier = Modifier.height(Space20))
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
            Text(
                text = "받은 마음 내역",
                style = Headline2.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = Space8)
            )
            Spacer(modifier = Modifier.height(Space20))
            TypingPriceField(
                textValue = moneyText,
                onValueChange = {
                    moneyText = it
                },
                isAddFiledEnabled = false,
            )
            Spacer(modifier = Modifier.height(Space24))

            FieldSubject(subject = "날짜")
            FieldSelectComponent(
                isSelected = isCalendarSelected,
                text = dateText,
                onClick = {
                    // TODO picker
                }
            )
            Spacer(modifier = Modifier.height(Space24))

            FieldSubject(subject = "경사 종류")

            Spacer(modifier = Modifier.height(Space24))

            FieldSubject(subject = "메모")

            Spacer(modifier = Modifier.height(Space24))

            FieldSubject("태그")
            HistoryRegistrationTagType.entries.chunked(5).forEach { registrationTagTypes ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    registrationTagTypes.forEach { type ->
                        ChipItem(
                            chipType = ChipType.BORDER,
                            chipText = type.tagName,
                            currentSelectedId = tagIdList.toSet(),
                            chipId = type.id,
                            onSelectChip = { selectId ->
                                if (tagIdList.contains(selectId)) {
                                    tagIdList.remove(selectId)
                                } else {
                                    tagIdList.add(selectId)
                                }
                            },
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun HistoryDetailEditScreenPreview() {
    HistoryDetailEditScreen(
        appState = rememberApplicationState(),
        model = HistoryDetailEditModel(state = HistoryDetailEditState.Init),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onResult = {}
    )
}
