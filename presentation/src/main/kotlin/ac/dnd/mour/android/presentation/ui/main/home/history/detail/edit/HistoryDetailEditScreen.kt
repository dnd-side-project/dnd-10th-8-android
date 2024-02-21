package ac.dnd.mour.android.presentation.ui.main.home.history.detail.edit

import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.theme.Space24
import ac.dnd.mour.android.presentation.common.theme.Space8
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.BottomSheetScreen
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import ac.dnd.mour.android.presentation.common.view.SnackBarScreen
import ac.dnd.mour.android.presentation.common.view.calendar.CalendarPicker
import ac.dnd.mour.android.presentation.common.view.chip.ChipItem
import ac.dnd.mour.android.presentation.common.view.chip.ChipType
import ac.dnd.mour.android.presentation.common.view.component.FieldSelectComponent
import ac.dnd.mour.android.presentation.common.view.component.FieldSubject
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.common.view.textfield.TypingPriceField
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.mour.android.presentation.model.history.HistoryTagType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.common.event.EventTypeScreen
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

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

@SuppressLint("CoroutineCreationDuringComposition")
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
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    var moneyText by remember { mutableStateOf(model.relatedHeart.money.toString()) }
    var selectedYear by remember { mutableIntStateOf(model.relatedHeart.day.year) }
    var selectedMonth by remember { mutableIntStateOf(model.relatedHeart.day.monthNumber) }
    var selectedDay by remember { mutableIntStateOf(model.relatedHeart.day.dayOfMonth) }
    var eventText by remember { mutableStateOf(model.relatedHeart.event) }
    var memoText by remember { mutableStateOf(model.relatedHeart.memo) }
    val tagIdList =
        remember { HistoryTagType.getTagIdList(model.relatedHeart.tags).toMutableStateList() }

    var isEditMode by remember { mutableStateOf(false) }
    var isEventSelected by remember { mutableStateOf(false) }
    var isEventFocused by remember { mutableStateOf(false) }
    var isEventTyping by remember { mutableStateOf(false) }
    var isMemoSelected by remember { mutableStateOf(false) }
    var isCalendarSelected by remember { mutableStateOf(false) }
    var isShowingOutPageDialog by remember { mutableStateOf(false) }
    var isShowingDeleteDialog by remember { mutableStateOf(false) }
    var isShowingSuccessEditSnackBar by remember { mutableStateOf(false) }

    BackHandler(
        enabled = true,
        onBack = {
            if (isEditMode) {
                isShowingOutPageDialog = true
            } else {
                onDismissRequest()
            }
        }
    )

    fun delete(event: HistoryDetailEditEvent.DeleteRelatedHeart) {
        when (event) {
            is HistoryDetailEditEvent.DeleteRelatedHeart.Success -> {
                onDelete(model.relatedHeart.id)
            }
        }
    }

    fun edit(event: HistoryDetailEditEvent.EditRelatedHeart) {
        when (event) {
            is HistoryDetailEditEvent.EditRelatedHeart.Success -> {
                onEdit(event.relatedHeart)
                scope.launch {
                    isShowingSuccessEditSnackBar = true
                    delay(500L)
                    isShowingSuccessEditSnackBar = false
                }
            }
        }
    }

    BottomSheetScreen(
        onDismissRequest = {
            isShowingOutPageDialog = true
        },
        properties = BottomSheetDialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true,
                isDraggable = false
            )
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray000)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(state = scrollState)
                    .padding(horizontal = Space20)
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
                                if (isEditMode) {
                                    isShowingOutPageDialog = true
                                } else {
                                    onDismissRequest()
                                }
                            }
                    )
                }
                Text(
                    text = "${if (model.relatedHeart.give) "보낸" else "받은"} 마음 내역",
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
                        isEditMode = true
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
                        isEditMode = true
                    }
                )
                Spacer(modifier = Modifier.height(Space24))

                FieldSubject(subject = "경사 종류")
                Spacer(modifier = Modifier.height(6.dp))
                TypingTextField(
                    text = eventText,
                    onValueChange = {
                        eventText = it
                        isEditMode = true
                    },
                    textType = TypingTextFieldType.Basic,
                    onTextFieldFocusChange = {
                        if (it) {
                            isEventSelected = true
                        } else {
                            isEventTyping = false
                        }
                        isEventFocused = it
                    },
                    trailingIconContent = {
                        if (isEventTyping) {
                            if (eventText.isNotEmpty()) {
                                Image(
                                    painter = painterResource(R.drawable.ic_close_circle),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {
                                            eventText = ""
                                        }
                                )
                            }
                        } else {
                            Image(
                                painter = painterResource(R.drawable.ic_chevron_right),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Gray500)
                            )
                        }
                    },
                    isSingleLine = true
                )
                Spacer(modifier = Modifier.height(Space24))

                FieldSubject(
                    subject = "메모",
                    isViewIcon = false
                )
                Spacer(modifier = Modifier.height(6.dp))
                TypingTextField(
                    text = memoText,
                    onValueChange = {
                        memoText = it
                        isEditMode = true
                    },
                    contentPadding = PaddingValues(
                        vertical = 15.dp,
                        horizontal = 16.dp
                    ),
                    fieldHeight = 97.dp,
                    onTextFieldFocusChange = {
                        isMemoSelected = it
                    },
                    textType = TypingTextFieldType.Basic,
                    trailingIconContent = {
                        if (isMemoSelected) {
                            if (memoText.isNotEmpty()) {
                                Image(
                                    painter = painterResource(R.drawable.ic_close_circle),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {
                                            memoText = ""
                                        }
                                )
                            }
                        } else {
                            Image(
                                painter = painterResource(R.drawable.ic_chevron_right),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Gray500)
                            )
                        }
                    },
                    isSingleLine = false
                )
                Spacer(modifier = Modifier.height(Space24))

                FieldSubject(
                    subject = "태그",
                    isViewIcon = false
                )
                Spacer(modifier = Modifier.height(6.dp))
                HistoryTagType.entries.chunked(5).forEach { registrationTagTypes ->
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        registrationTagTypes.forEach { type ->
                            ChipItem(
                                chipType = ChipType.BORDER,
                                chipText = type.tagName,
                                currentSelectedId = tagIdList.toSet(),
                                chipId = type.id,
                                onSelectChip = { selectId ->
                                    isEditMode = true
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
                    .padding(
                        vertical = Space12,
                        horizontal = Space20
                    ),
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
                                        tags = HistoryTagType.getTagNameList(tagIdList)
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
            if (isShowingSuccessEditSnackBar) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 61.dp)
                ) {
                    SnackBarScreen("수정이 완료되었습니다.")
                }
            }
        }
    }

    if (isEventSelected) {
        EventTypeScreen(
            onDismissRequest = {
                isEventSelected = false
            },
            onConfirm = {
                isEventSelected = false
                eventText = it
            },
            onTypingMode = {
                isEventSelected = false
                isEventTyping = true
            }
        )
    }

    if (isCalendarSelected) {
        CalendarPicker(
            localDate = LocalDate(
                year = selectedYear,
                monthNumber = selectedMonth,
                dayOfMonth = selectedDay
            ),
            isDaySelectable = true,
            onDismissRequest = {
                isCalendarSelected = false
            },
            onConfirm = {
                selectedYear = it.year
                selectedMonth = it.monthNumber
                selectedDay = it.dayOfMonth
            }
        )
    }

    if (isShowingOutPageDialog) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                isShowingOutPageDialog = false
            }
        ) {
            Card(
                backgroundColor = Color.White,
                shape = Shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_alert_triangle_gray),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "페이지를 나가면\n수정중인 내용이 삭제돼요.",
                        style = Body1.merge(
                            color = Gray800,
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(modifier = Modifier.wrapContentSize()) {
                        ConfirmButton(
                            properties = ConfirmButtonProperties(
                                size = ConfirmButtonSize.Large,
                                type = ConfirmButtonType.Secondary
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onDismissRequest()
                                isShowingOutPageDialog = false
                            }
                        ) {
                            Text(
                                text = "나가기",
                                style = Headline3.merge(
                                    color = Gray700,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        ConfirmButton(
                            properties = ConfirmButtonProperties(
                                size = ConfirmButtonSize.Large,
                                type = ConfirmButtonType.Primary
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                isShowingOutPageDialog = false
                            }
                        ) {
                            Text(
                                text = "계속 수정",
                                style = Headline3.merge(
                                    color = Gray000,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    if (isShowingDeleteDialog) {
        DialogScreen(
            isCancelable = true,
            message = "${if (model.relatedHeart.give) "보낸" else "받은"} 마음 내역을 삭제하시겠어요?",
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
                memo = "답례품으로 와인을 받았다답례품으로 와인을 받았다답례품으로 와인을 받았다",
                tags = listOf(
                    HistoryTagType.ATTEND.tagName
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
