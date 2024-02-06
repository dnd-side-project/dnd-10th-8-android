package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.edit

import ac.dnd.bookkeeping.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space24
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.common.view.calendar.CalendarConfig
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipItem
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipType
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSelectComponent
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSubject
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingPriceField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryRegistrationTagType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.common.calendar.EventTypeScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.datetime.LocalDate
import java.text.DecimalFormat

@Composable
fun HistoryDetailEditScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onDelete: (Long) -> Unit,
    onEdit: (RelatedHeart) -> Unit,
    relatedHeart: RelatedHeart,
    viewModel: HistoryDetailEditViewModel = hiltViewModel()
) {
    val model: HistoryDetailEditModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        HistoryDetailEditModel(
            state = state,
            relatedHeart = relatedHeart
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
        onDelete = onDelete,
        onEdit = onEdit
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
    onDelete: (Long) -> Unit,
    onEdit: (RelatedHeart) -> Unit,
    calendarConfig: CalendarConfig = CalendarConfig()
) {
    val scope = rememberCoroutineScope()
    var scrollState = rememberScrollState()
    var moneyText by remember {
        mutableStateOf("${DecimalFormat("#,###").format(model.relatedHeart.money)}원")
    }
    var selectedYear by remember { mutableIntStateOf(model.relatedHeart.day.year) }
    var selectedMonth by remember { mutableIntStateOf(model.relatedHeart.day.monthNumber) }
    var selectedDay by remember { mutableIntStateOf(model.relatedHeart.day.dayOfMonth) }
    var eventText by remember { mutableStateOf(model.relatedHeart.event) }
    var memoText by remember { mutableStateOf(model.relatedHeart.memo) }
    val tagIdList = remember {
        HistoryRegistrationTagType.getTagIdList(model.relatedHeart.tags).toMutableStateList()
    }

    var isEventSelected by remember { mutableStateOf(false) }
    var isMemoSelected by remember { mutableStateOf(false) }
    var isCalendarSelected by remember { mutableStateOf(false) }
    var isShowingOutPageDialog by remember { mutableStateOf(false) }
    var isShowingDeleteDialog by remember { mutableStateOf(false) }
    var isShowingSuccessDeleteDialog by remember { mutableStateOf(false) }
    var isShowingSuccessEditDialog by remember { mutableStateOf(false) }

    fun delete(event: HistoryDetailEditEvent.DeleteRelatedHeart) {
        when (event) {
            is HistoryDetailEditEvent.DeleteRelatedHeart.Success -> {
                isShowingSuccessDeleteDialog = true
                onDelete(model.relatedHeart.id)
            }
        }
    }

    fun edit(event: HistoryDetailEditEvent.EditRelatedHeart) {
        when (event) {
            is HistoryDetailEditEvent.EditRelatedHeart.Success -> {
                isShowingSuccessEditDialog = true
                onEdit(event.relatedHeart)
            }
        }
    }

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray000)
                .padding(horizontal = Space20)
        ) {
            Column(
                modifier = Modifier.verticalScroll(state = scrollState)
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
                Spacer(modifier = Modifier.height(6.dp))
                FieldSelectComponent(
                    isSelected = isCalendarSelected,
                    text = listOf(selectedYear, selectedMonth, selectedDay).joinToString(" / "),
                    onClick = {
                        isCalendarSelected = true
                        // TODO picker
                    }
                )
                Spacer(modifier = Modifier.height(Space24))

                FieldSubject(subject = "경사 종류")
                Spacer(modifier = Modifier.height(6.dp))
                FieldSelectComponent(
                    isSelected = isEventSelected,
                    text = eventText,
                    onClick = {
                        isEventSelected = true
                    }
                )
                Spacer(modifier = Modifier.height(Space24))

                FieldSubject(subject = "메모")
                Spacer(modifier = Modifier.height(6.dp))
                if (!isMemoSelected) {
                    FieldSelectComponent(
                        isSelected = isMemoSelected,
                        text = memoText,
                        onClick = {
                            isMemoSelected = true
                        }
                    )
                } else {
                    // TODO memo focus out -> isMemoSelected = false
                    TypingTextField(
                        textType = TypingTextFieldType.LongSentence,
                        text = memoText,
                        onValueChange = {
                            memoText = it
                        }
                    )
                }
                Spacer(modifier = Modifier.height(Space24))

                FieldSubject("태그")
                Spacer(modifier = Modifier.height(6.dp))
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

                Spacer(modifier = Modifier.height(98.dp))
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Gray000)
                    .padding(vertical = Space12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(Shapes.large)
                        .border(
                            width = 1.dp,
                            color = Gray400,
                            shape = Shapes.large
                        )
                        .clickable {
                            isShowingDeleteDialog = true
                        }
                        .padding(
                            vertical = 14.dp,
                            horizontal = 24.dp
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_trash),
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentDescription = null,
                        modifier = Modifier.size(Space24)
                    )
                }
                Spacer(modifier = Modifier.width(Space12))
                ConfirmButton(
                    modifier = Modifier.weight(1f),
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Xlarge,
                        type = ConfirmButtonType.Primary
                    ),
                    onClick = {
                        if (
                            checkRegistrable(
                                money = moneyText.toLongOrNull() ?: 0L,
                                event = eventText
                            )
                        ) {
                            intent(
                                HistoryDetailEditIntent
                                    .OnEdit(
                                        id = model.relatedHeart.id,
                                        money = moneyText.toLongOrNull() ?: 0L,
                                        give = model.relatedHeart.give,
                                        day = LocalDate(
                                            year = selectedYear,
                                            monthNumber = selectedMonth,
                                            dayOfMonth = selectedDay
                                        ),
                                        event = eventText,
                                        memo = memoText,
                                        tags = HistoryRegistrationTagType.getTagNameList(tagIdList)
                                    )
                            )
                        }
                    }
                ) {
                    Text(
                        text = "확인",
                        style = Headline3.merge(
                            color = Gray000,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }

    if (isEventSelected){
        EventTypeScreen(
            onDismissRequest = {
                isEventSelected = false
            },
            onConfirm = {
                isEventSelected = false
                eventText = it
            }
        )
    }

    if (isShowingOutPageDialog) {
        DialogScreen(
            isCancelable = true,
            title = "페이지를 나가면 수정중인 내용이 삭제돼요.",
            confirmMessage = "나가기",
            cancelMessage = "계속 수정",
            onCancel = {
                onDismissRequest()
            },
            onConfirm = {
                isShowingOutPageDialog = false
            },
            onDismissRequest = {
                isShowingOutPageDialog = false
            }
        )
    }

    if (isShowingDeleteDialog) {
        DialogScreen(
            isCancelable = true,
            title = "${if (model.relatedHeart.give) "보낸" else "받은"} 마음 내역을 삭제하시겠어요?",
            confirmMessage = "삭제",
            cancelMessage = "취소",
            onCancel = {
                isShowingDeleteDialog = false
            },
            onConfirm = {
                intent(HistoryDetailEditIntent.OnDelete(model.relatedHeart.id))
                isShowingDeleteDialog = false
            },
            onDismissRequest = {
                isShowingDeleteDialog = false
            }
        )
    }
    if (isShowingSuccessDeleteDialog) {
        DialogScreen(
            isCancelable = false,
            title = "삭제가 완료되었습니다.",
            confirmMessage = "확인",
            onConfirm = {
                isShowingSuccessDeleteDialog = false
            },
            onDismissRequest = {
                isShowingSuccessDeleteDialog = false
            }
        )
    }
    if (isShowingSuccessEditDialog) {
        DialogScreen(
            isCancelable = false,
            title = "수정이 완료되었습니다.",
            confirmMessage = "확인",
            onConfirm = {
                isShowingSuccessEditDialog = false
            },
            onDismissRequest = {
                isShowingSuccessEditDialog = false
            }
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is HistoryDetailEditEvent.EditRelatedHeart.Success -> edit(event)
                is HistoryDetailEditEvent.DeleteRelatedHeart.Success -> delete(event)
            }
        }
    }
}

private fun checkRegistrable(
    money: Long,
    event: String
): Boolean {
    return money != 0L && event.isNotEmpty()
}

@Preview(apiLevel = 33)
@Composable
private fun HistoryDetailEditScreenPreview() {
    HistoryDetailEditScreen(
        appState = rememberApplicationState(),
        model = HistoryDetailEditModel(
            state = HistoryDetailEditState.Init,
            relatedHeart = RelatedHeart(
                id = 0,
                give = true,
                money = 500_000L,
                day = LocalDate(2023, 1, 1),
                event = "참석",
                memo = "답례품으로 와인을 받았다",
                tags = listOf(
                    HistoryRegistrationTagType.ATTEND.tagName
                )
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onDelete = {},
        onEdit = {}
    )
}
