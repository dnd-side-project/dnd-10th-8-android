package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.notification

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Alarm
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRelationGroup
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Body2
import ac.dnd.bookkeeping.android.presentation.common.theme.Caption2
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Icon24
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

@Composable
fun NotificationScreen(
    appState: ApplicationState,
    model: NotificationModel,
    event: EventFlow<NotificationEvent>,
    intent: (NotificationIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()

    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val (recentAlarmList, lateAlarmList) = model.alarmList.filter {
        it.alarm.date.plus(30, DateTimeUnit.DAY) > now && it.alarm.date < now
    }.partition {
        it.alarm.date.plus(7, DateTimeUnit.DAY) > now
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
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
                text = "알림",
                style = Headline1
            )
        }
        if (model.alarmList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(R.drawable.ic_add_chart),
                    contentDescription = null,
                    tint = Color(0xFFDDDEE1)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "새로운 알림이 없어요",
                    style = Body1.merge(Gray700)
                )
            }
        } else {
            Spacer(modifier = Modifier.height(11.dp))
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = "최근 7일",
                            style = Body1.merge(Gray800)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Gray400
                        )
                    }
                }
                items(
                    items = recentAlarmList,
                    key = { it.id }
                ) { item ->
                    NotificationScreenItem(
                        item = item,
                        handler = handler
                    )
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = "이전 알림",
                            style = Body1.merge(Gray800)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Gray400
                        )
                    }
                }
                items(
                    items = lateAlarmList,
                    key = { it.id }
                ) { item ->
                    NotificationScreenItem(
                        item = item,
                        handler = handler
                    )
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = "받은 소식은 30일 동안 보관됩니다",
                            style = Body2.merge(Gray600)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun NotificationScreenItem(
    item: Alarm,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()

    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val formattedDate = when {
        item.alarm.date.plus(7, DateTimeUnit.DAY) > now -> {
            "${item.alarm.date.daysUntil(now)}일 전"
        }

        item.alarm.date.year == now.year -> {
            val format = "%02d월 %02d일"
            runCatching {
                String.format(format, item.alarm.date.month.number, item.alarm.date.dayOfMonth)
            }.onFailure { exception ->
                scope.launch(handler) {
                    throw exception
                }
            }.getOrDefault("??월 ??일")
        }

        else -> {
            val format = "%02d년 %02d월 %02d일"
            runCatching {
                String.format(
                    format,
                    item.alarm.date.year % 100,
                    item.alarm.date.year,
                    item.alarm.date.month.number
                )
            }.onFailure { exception ->
                scope.launch(handler) {
                    throw exception
                }
            }.getOrDefault("??년 ??월 ??일")
        }
    }
    val formattedContent = Unit.let {
        val date = Unit.let {
            val format = "%04d. %02d. %02d"
            runCatching {
                String.format(format, item.day.year, item.day.month.number, item.day.dayOfMonth)
            }.onFailure { exception ->
                scope.launch(handler) {
                    throw exception
                }
            }.getOrDefault("????. ??. ??")
        }
        val time = item.time?.let {
            val fixedHour = if (it.hour == 0) 24 else it.hour
            val timeHour = (fixedHour - 1) % 12 + 1
            val timeMinute = it.minute
            val timeAmPm = if (fixedHour < 12) "오전" else "오후"
            val format = "%s %02d시 %02d분"
            runCatching {
                String.format(format, timeAmPm, timeHour, timeMinute)
            }.getOrDefault("시간 없음")
        } ?: "시간 없음"

        if (item.time == null) {
            "$date ${item.relation.name}님의 ${item.event} 일정이 있습니다."
        } else {
            "$date ${time}에 ${item.relation.name}님의 ${item.event} 일정이 있습니다."
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Primary1)
                .size(40.dp),
        ) {
            Icon(
                modifier = Modifier
                    .size(Space20)
                    .align(Alignment.Center),
                painter = painterResource(R.drawable.ic_schedule),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(11.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "미리 알림",
                    style = Body1
                )
                Text(
                    text = formattedDate,
                    style = Body2
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = formattedContent,
                style = Body1
            )
            if (item.location.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        tint = Gray600
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = item.location,
                        style = Caption2.merge(Gray700)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NotificationScreenPreview1() {
    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val date1 = now.minus(1, DateTimeUnit.DAY)
    val date2 = now.minus(10, DateTimeUnit.DAY)
    NotificationScreen(
        appState = rememberApplicationState(),
        model = NotificationModel(
            state = NotificationState.Init,
            alarmList = listOf(
                Alarm(
                    id = 9839,
                    relation = AlarmRelation(
                        id = 8121,
                        name = "Seth Sears",
                        group = AlarmRelationGroup(
                            id = 9854,
                            name = "Pete Lambert"
                        )
                    ),
                    day = date1,
                    event = "doctus",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(date1.year, date1.month.number, date1.dayOfMonth, 9, 0),
                    time = null,
                    link = "urna",
                    location = "quas",
                    memo = "error"
                ),
                Alarm(
                    id = 98319,
                    relation = AlarmRelation(
                        id = 81211,
                        name = "Seth Sears2",
                        group = AlarmRelationGroup(
                            id = 98154,
                            name = "Pete Lambert2"
                        )
                    ),
                    day = date2,
                    event = "결혼",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(date2.year, date2.month.number, date2.dayOfMonth, 12, 0),
                    time = LocalTime(14, 0),
                    link = "",
                    location = "",
                    memo = ""
                ),
                Alarm(
                    id = 983129,
                    relation = AlarmRelation(
                        id = 812211,
                        name = "Seth Sears3",
                        group = AlarmRelationGroup(
                            id = 981254,
                            name = "Pete Lambert3"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "결혼",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = LocalTime(14, 0),
                    link = "",
                    location = "",
                    memo = ""
                )
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}

@Preview
@Composable
fun NotificationScreenPreview2() {
    NotificationScreen(
        appState = rememberApplicationState(),
        model = NotificationModel(
            state = NotificationState.Init,
            alarmList = emptyList()
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
