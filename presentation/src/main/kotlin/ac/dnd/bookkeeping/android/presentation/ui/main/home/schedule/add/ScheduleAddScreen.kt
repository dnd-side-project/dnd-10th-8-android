package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body0
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
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
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.notification.NotificationConstant
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
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
    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val eventTypeList: List<HistoryEventType> = HistoryEventType.entries

    // TODO : rememberSaveable 을 위한 Parcelable Presentation Model
    var relation: RelationDetail? by remember { mutableStateOf(null) }
    var date: LocalDate by remember { mutableStateOf(now) }
    var eventName: String by remember { mutableStateOf("") }
    var alarm: LocalDateTime? by remember { mutableStateOf(null) }
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
    var isAlarmRepeatPickerShowing: Boolean by remember { mutableStateOf(false) }
    var isRepeatFinishDatePickerShowing: Boolean by remember { mutableStateOf(false) }
    var isTimePickerShowing: Boolean by remember { mutableStateOf(false) }

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
    val formattedAlarm = Unit.let {
        alarm?.let {
            val alarmDay = when (
                val difference = (it.date - now).days
            ) {
                0 -> "당일"
                in 1..6 -> "${difference}일 전"
                else -> "${difference / 7}주 전"
            }
            val fixedHour = if (it.time.hour == 0) 24 else it.time.hour
            val alarmHour = (fixedHour + 1) % 12 - 1
            val alarmMinute = it.time.minute
            val alarmAmPm = if (fixedHour < 13) "AM" else "PM"
            "$alarmDay $alarmHour:$alarmMinute $alarmAmPm"
        } ?: "알림 없음"
    }
    val formattedTime = Unit.let {
        time?.let {
            val fixedHour = if (it.hour == 0) 24 else it.hour
            val timeHour = (fixedHour + 1) % 12 - 1
            val timeMinute = it.minute
            val timeAmPm = if (fixedHour < 13) "오전" else "오후"
            "$timeAmPm $timeHour:$timeMinute"
        } ?: "시간 없음"
    }
    val formattedRepeatType = when (repeatType) {
        AlarmRepeatType.Month -> "매달"
        AlarmRepeatType.Year -> "매년"
        null -> "반복 없음"
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
                text = "일정 추가하기",
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
                            style = Headline3
                        )
                        Text(
                            text = "・",
                            style = Headline3
                        )
                        Text(
                            text = relation?.group?.name.orEmpty(),
                            style = Body0
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
                isSelected = isAlarmRepeatPickerShowing,
                text = formattedRepeatType,
                onClick = {
                    isAlarmRepeatPickerShowing = true
                }
            )
            if (repeatType != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(43.dp),
                    backgroundColor = Gray200,
                    shape = Shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "반복 종료",
                            modifier = Modifier.weight(1f),
                            style = Body1
                        )
                        Switch(
                            checked = isRepeatFinish,
                            onCheckedChange = { isChecked ->
                                isRepeatFinish = isChecked
                            }
                        )
                    }
                }
            }
            if (isRepeatFinish) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(43.dp)
                        .clickable {
                            isRepeatFinishDatePickerShowing = true
                        },
                    backgroundColor = Gray200,
                    shape = Shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "종료일",
                            modifier = Modifier.weight(1f),
                            style = Body1
                        )
                        Text(
                            text = "설정하기",
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
                hintText = "경사 관련 메모를 입력해주세요"
            )
        }
        Spacer(modifier = Modifier.height(46.dp))
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .fillMaxWidth(),
            onClick = {
                intent(ScheduleAddIntent.OnConfirm)
            }
        ) { style ->
            Text(
                text = "저장하기",
                style = style
            )
        }
    }

    fun navigateToAddSchedule() {
        appState.navController.navigate(ScheduleAddConstant.ROUTE)
    }

    fun navigateToNotification() {
        appState.navController.navigate(NotificationConstant.ROUTE)
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
fun ScheduleAddScreenPreview() {
    ScheduleAddScreen(
        appState = rememberApplicationState(),
        model = ScheduleAddModel(
            state = ScheduleAddState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
