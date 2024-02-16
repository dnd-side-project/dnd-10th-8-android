package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationSimple
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelationGroup
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body0
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Icon24
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.common.view.calendar.CalendarPicker
import ac.dnd.bookkeeping.android.presentation.common.view.calendar.TimePicker
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipItem
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipType
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSelectComponent
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryEventType
import ac.dnd.bookkeeping.android.presentation.model.schedule.ScheduleAlarmType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.get.SearchRelationScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.notification.ScheduleAddNotificationScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.repeat.ScheduleAddRepeatScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn

@Composable
fun ScheduleAddScreen(
    appState: ApplicationState,
    model: ScheduleAddModel,
    event: EventFlow<ScheduleAddEvent>,
    intent: (ScheduleAddIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val eventTypeList: List<HistoryEventType> = HistoryEventType.entries

    // TODO : rememberSaveable 을 위한 Parcelable Presentation Model
    var relation: RelationSimple? by remember { mutableStateOf(null) }
    var date: LocalDate by remember { mutableStateOf(now) }
    var eventName: String by remember { mutableStateOf("") }
    var alarm: ScheduleAlarmType by remember { mutableStateOf(ScheduleAlarmType.None) }
    var repeatType: AlarmRepeatType? by remember { mutableStateOf(null) }
    var isRepeatFinish: Boolean by remember { mutableStateOf(false) }
    var repeatFinish: LocalDate? by remember { mutableStateOf(null) }
    var time: LocalTime? by remember { mutableStateOf(null) }
    var location: String by remember { mutableStateOf("") }
    var link: String by remember { mutableStateOf("") }
    var memo: String by remember { mutableStateOf("") }

    var isGetRelationShowing: Boolean by remember { mutableStateOf(false) }
    var isDatePickerShowing: Boolean by remember { mutableStateOf(false) }
    var isAlarmDatePickerShowing: Boolean by remember { mutableStateOf(false) }
    var isRepeatPickerShowing: Boolean by remember { mutableStateOf(false) }
    var isRepeatFinishDatePickerShowing: Boolean by remember { mutableStateOf(false) }
    var isTimePickerShowing: Boolean by remember { mutableStateOf(false) }
    var isAddSuccessShowing: Boolean by remember { mutableStateOf(false) }
    var isEditSuccessShowing: Boolean by remember { mutableStateOf(false) }
    var isRemoveSuccessShowing: Boolean by remember { mutableStateOf(false) }

    val isConfirmEnabled: Boolean =
        relation != null && eventName.isNotBlank() && model.state != ScheduleAddState.Loading

    fun onRemove() {
        intent(ScheduleAddIntent.OnRemove)
    }

    fun onConfirm() {
        if (!isConfirmEnabled) return
        intent(
            ScheduleAddIntent.OnConfirm(
                relationId = relation?.id ?: -1,
                day = date,
                event = eventName,
                repeatType = repeatType,
                repeatFinish = repeatFinish,
                alarm = alarm,
                time = time,
                link = link,
                location = location,
                memo = memo
            )
        )
    }

    if (isGetRelationShowing) {
        SearchRelationScreen(
            onDismissRequest = { isGetRelationShowing = false },
            appState = appState,
            onResult = {
                relation = it
            }
        )
    }

    if (isDatePickerShowing) {
        CalendarPicker(
            localDate = now,
            isDaySelectable = true,
            onDismissRequest = { isDatePickerShowing = false },
            onConfirm = {
                date = it
            }
        )
    }

    if (isAlarmDatePickerShowing) {
        ScheduleAddNotificationScreen(
            appState = appState,
            onDismissRequest = { isAlarmDatePickerShowing = false },
            initialAlarmType = alarm,
            onResult = {
                alarm = it
            }
        )
    }

    if (isRepeatPickerShowing) {
        ScheduleAddRepeatScreen(
            appState = appState,
            initialSelectedType = repeatType,
            onDismissRequest = { isRepeatPickerShowing = false },
            onResult = {
                repeatType = it
            }
        )
    }

    if (isRepeatFinishDatePickerShowing) {
        CalendarPicker(
            localDate = now,
            isDaySelectable = true,
            onDismissRequest = { isRepeatFinishDatePickerShowing = false },
            onConfirm = {
                repeatFinish = it
            }
        )
    }

    if (isTimePickerShowing) {
        TimePicker(
            localTime = time ?: LocalTime(0, 0),
            onDismissRequest = { isTimePickerShowing = false },
            onConfirm = {
                time = it
            }
        )
    }

    if (isAddSuccessShowing) {
        DialogScreen(
            title = "일정 추가하기",
            message = "일정을 추가하였습니다.",
            isCancelable = false,
            onConfirm = {
                appState.navController.navigateUp()
            },
            onDismissRequest = {
                isAddSuccessShowing = false
            }
        )
    }

    if (isEditSuccessShowing) {
        DialogScreen(
            title = "일정 수정하기",
            message = "일정을 수정하였습니다.",
            isCancelable = false,
            onConfirm = {
                appState.navController.navigateUp()
            },
            onDismissRequest = {
                isEditSuccessShowing = false
            }
        )
    }

    if (isRemoveSuccessShowing) {
        DialogScreen(
            title = "일정 수정하기",
            message = "일정을 제거하였습니다.",
            isCancelable = false,
            onConfirm = {
                appState.navController.navigateUp()
            },
            onDismissRequest = {
                isRemoveSuccessShowing = false
            }
        )
    }

    val formattedDate = Unit.let {
        val format = "%04d / %02d / %02d"
        val year = date.year
        val month = date.month.number
        val day = date.dayOfMonth
        runCatching {
            String.format(format, year, month, day)
        }.onFailure { exception ->
            scope.launch(handler) {
                throw exception
            }
        }.getOrDefault("???? / ?? / ??")
    }
    val currentEvent: HistoryEventType? = eventTypeList.find {
        it.eventName == eventName
    }
    val formattedAlarm = alarm.text
    val formattedTime = Unit.let {
        time?.let {
            val fixedHour = if (it.hour == 0) 24 else it.hour
            val timeHour = (fixedHour - 1) % 12 + 1
            val timeMinute = it.minute
            val timeAmPm = if (fixedHour < 12) "오전" else "오후"
            "$timeAmPm $timeHour:$timeMinute"
        } ?: "시간 없음"
    }
    val formattedRepeatType = when (repeatType) {
        AlarmRepeatType.Month -> "매월"
        AlarmRepeatType.Year -> "매년"
        null -> "반복 없음"
    }
    val formattedRepeatFinish = Unit.let {
        repeatFinish?.let {
            val format = "%04d.%02d.%02d"
            val year = it.year
            val month = it.month.number
            val day = it.dayOfMonth
            runCatching {
                String.format(format, year, month, day)
            }.onFailure { exception ->
                scope.launch(handler) {
                    throw exception
                }
            }.getOrDefault("????.??.??")
        } ?: "설정하기"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = {
                        appState.navController.navigateUp()
                    }
                )
            ) {
                Icon(
                    modifier = Modifier.size(Icon24),
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = null
                )
            }
            Text(
                text = if (model.schedule == null) "일정 추가하기" else "일정 수정하기",
                style = Headline1
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row {
                Text(
                    text = "이름",
                    style = Body1
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "*",
                    style = Body1.merge(color = Negative)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            if (relation == null) {
                FieldSelectComponent(
                    isSelected = isGetRelationShowing,
                    text = "이름 선택",
                    onClick = {
                        isGetRelationShowing = true
                    }
                )
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            isGetRelationShowing = true
                        },
                    backgroundColor = Primary1,
                    shape = Shapes.medium,
                    border = BorderStroke(1.dp, Primary4),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = relation?.name.orEmpty(),
                            style = Headline3.merge(Gray800)
                        )
                        Text(
                            text = "・",
                            style = Headline3.merge(Gray800)
                        )
                        Text(
                            text = relation?.group?.name.orEmpty(),
                            style = Body0.merge(Gray800)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            modifier = Modifier.size(Icon24),
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = null,
                            tint = Gray600
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "날짜",
                    style = Body1
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "*",
                    style = Body1.merge(color = Negative)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            FieldSelectComponent(
                isSelected = isDatePickerShowing,
                text = formattedDate,
                onClick = {
                    isDatePickerShowing = true
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "경사 종류",
                    style = Body1
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "*",
                    style = Body1.merge(color = Negative)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = eventName,
                onValueChange = { text ->
                    eventName = text
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { isAlarmDatePickerShowing = true }
                ),
                hintText = "직접 입력"
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(eventTypeList.size) { index ->
                    val item = eventTypeList[index]
                    ChipItem(
                        chipType = ChipType.BORDER,
                        currentSelectedId = currentEvent?.let { setOf(it.id) }.orEmpty(),
                        chipId = item.id,
                        chipText = item.eventName,
                        chipCount = 0,
                        onSelectChip = { id ->
                            eventName = eventTypeList.find { it.id == id }?.eventName.orEmpty()
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(46.dp))
        Divider(
            modifier = Modifier.height(8.dp),
            color = Gray200
        )
        Spacer(modifier = Modifier.height(46.dp))
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    tint = Gray600
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "미리 알림",
                    style = Body1
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            FieldSelectComponent(
                isSelected = isAlarmDatePickerShowing,
                text = formattedAlarm,
                onClick = {
                    isAlarmDatePickerShowing = true
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_repeat),
                    contentDescription = null,
                    tint = Gray600
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "반복",
                    style = Body1
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            FieldSelectComponent(
                isSelected = isRepeatPickerShowing,
                text = formattedRepeatType,
                onClick = {
                    isRepeatPickerShowing = true
                }
            )
            if (repeatType != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                            text = "반복 종료",
                            modifier = Modifier.weight(1f),
                            style = Body1
                        )
                        Column {
                            Switch(
                                checked = isRepeatFinish,
                                modifier = Modifier.weight(1f),
                                onCheckedChange = { isChecked ->
                                    isRepeatFinish = isChecked
                                }
                            )
                        }
                    }
                }
            }
            if (isRepeatFinish) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isRepeatFinishDatePickerShowing = true
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
                            text = "종료일",
                            modifier = Modifier.weight(1f),
                            style = Body1
                        )
                        Text(
                            text = formattedRepeatFinish,
                            style = Body1.merge(Gray700)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            modifier = Modifier.size(Icon24),
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = null,
                            tint = Gray600
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = null,
                    tint = Gray600
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "시간",
                    style = Body1
                )
            }
            FieldSelectComponent(
                isSelected = isTimePickerShowing,
                text = formattedTime,
                onClick = {
                    isTimePickerShowing = true
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = null,
                    tint = Gray600
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "위치",
                    style = Body1
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = location,
                onValueChange = { text ->
                    location = text
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                hintText = "위치를 입력해주세요"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = null,
                    tint = Gray600
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "초대장 링크",
                    style = Body1
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = link,
                onValueChange = { text ->
                    link = text
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                hintText = "모바일 초대장 링크를 입력해주세요"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_memo),
                    contentDescription = null,
                    tint = Gray600
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "메모",
                    style = Body1
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            TypingTextField(
                textType = TypingTextFieldType.LongSentence,
                text = memo,
                onValueChange = { text ->
                    memo = text
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onConfirm()
                        focusManager.clearFocus()
                    }
                ),
                hintText = "경사 관련 메모를 입력해주세요"
            )
        }
        Spacer(modifier = Modifier.height(46.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            if (model.schedule != null) {
                ConfirmButton(
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Large,
                        type = ConfirmButtonType.Secondary
                    ),
                    isEnabled = isConfirmEnabled,
                    onClick = {
                        onRemove()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(Icon24),
                        painter = painterResource(id = R.drawable.ic_trash),
                        contentDescription = null
                    )
                }
            }
            ConfirmButton(
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Large,
                    type = ConfirmButtonType.Primary
                ),
                modifier = Modifier.weight(1f),
                isEnabled = isConfirmEnabled,
                onClick = {
                    onConfirm()
                }
            ) { style ->
                Text(
                    text = "저장하기",
                    style = style
                )
            }
        }
    }

    fun addSchedule(event: ScheduleAddEvent.AddSchedule) {
        when (event) {
            ScheduleAddEvent.AddSchedule.Success -> {
                isAddSuccessShowing = true
            }
        }
    }

    fun editSchedule(event: ScheduleAddEvent.EditSchedule) {
        when (event) {
            ScheduleAddEvent.EditSchedule.Success -> {
                isEditSuccessShowing = true
            }
        }
    }

    fun removeSchedule(event: ScheduleAddEvent.RemoveSchedule) {
        when (event) {
            ScheduleAddEvent.RemoveSchedule.Success -> {
                isRemoveSuccessShowing = true
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is ScheduleAddEvent.AddSchedule -> {
                    addSchedule(event)
                }

                is ScheduleAddEvent.EditSchedule -> {
                    editSchedule(event)
                }

                is ScheduleAddEvent.RemoveSchedule -> {
                    removeSchedule(event)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ScheduleAddScreenPreview1() {
    ScheduleAddScreen(
        appState = rememberApplicationState(),
        model = ScheduleAddModel(
            state = ScheduleAddState.Init,
            schedule = null
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}

@Preview
@Composable
private fun ScheduleAddScreenPreview2() {
    ScheduleAddScreen(
        appState = rememberApplicationState(),
        model = ScheduleAddModel(
            state = ScheduleAddState.Init,
            schedule = Schedule(
                id = 9278,
                relation = ScheduleRelation(
                    id = 8408,
                    name = "장성혁",
                    group = ScheduleRelationGroup(
                        id = 7385,
                        name = "친구"
                    )
                ),
                day = LocalDate(2024, 2, 25),
                event = "생일",
                time = LocalTime(9, 0),
                link = "https://www.google.com/",
                location = "서울특별시 강남구 역삼동",
                memo = "메모입니다."
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
