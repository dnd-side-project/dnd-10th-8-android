package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelationGroup
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryEventType
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus

@Composable
fun ScheduleScreenHeader(
    appState: ApplicationState,
    model: ScheduleModel,
    event: EventFlow<ScheduleEvent>,
    intent: (ScheduleIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    showingDate: LocalDate,
    selectedDate: LocalDate,
    onClickDate: () -> Unit,
    onClickDay: (LocalDate) -> Unit
) {
    var isExpanded: Boolean by remember { mutableStateOf(true) }

    val formattedDate = Unit.let {
        val year = showingDate.year
        val month = showingDate.month.number

        "${year}년 ${month}월"
    }

    Card(
        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
        backgroundColor = Gray000,
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.clickable {
                    onClickDate()
                },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formattedDate,
                    style = Headline1
                )
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.ic_drop_down),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ScheduleScreenHeaderCalendar(
                appState = appState,
                model = model,
                event = event,
                intent = intent,
                handler = handler,
                showingDate = showingDate,
                selectedDate = selectedDate,
                isExpanded = isExpanded,
                onClickDay = onClickDay
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = Shapes.medium,
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            isExpanded = !isExpanded
                        }
                    ) {
                        Text(
                            text = if (isExpanded) {
                                "작게보기"
                            } else {
                                "펼쳐보기"
                            },
                            style = Body1.merge(Gray700)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = if (isExpanded) {
                                painterResource(R.drawable.ic_chevron_up)
                            } else {
                                painterResource(R.drawable.ic_chevron_down)
                            },
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ScheduleScreenHeaderCalendar(
    appState: ApplicationState,
    model: ScheduleModel,
    event: EventFlow<ScheduleEvent>,
    intent: (ScheduleIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    showingDate: LocalDate,
    selectedDate: LocalDate,
    isExpanded: Boolean,
    onClickDay: (LocalDate) -> Unit
) {
    val previousDateList: List<LocalDate> = Unit.let {
        val firstDay = LocalDate(showingDate.year, showingDate.month, 1)
        val previousMonthLastDay = firstDay.minus(1, DateTimeUnit.DAY)
        val previousMonthDayList =
            (previousMonthLastDay.dayOfMonth - firstDay.dayOfWeek.ordinal + 1..previousMonthLastDay.dayOfMonth).map {
                LocalDate(previousMonthLastDay.year, previousMonthLastDay.month, it)
            }
        previousMonthDayList
    }
    val currentDateList: List<LocalDate> = Unit.let {
        val firstDay = LocalDate(showingDate.year, showingDate.month, 1)
        val lastDay = LocalDate(showingDate.year, showingDate.month, 1)
            .plus(1, DateTimeUnit.MONTH)
            .minus(1, DateTimeUnit.DAY)
        val currentMonthDayList = (firstDay.dayOfMonth..lastDay.dayOfMonth).map {
            LocalDate(showingDate.year, showingDate.month, it)
        }
        currentMonthDayList
    }
    val nextDateList: List<LocalDate> = Unit.let {
        val nextMonthFirstDay =
            LocalDate(showingDate.year, showingDate.month, 1).plus(1, DateTimeUnit.MONTH)
        val lastDay = nextMonthFirstDay.minus(1, DateTimeUnit.DAY)
        val nextMonthDayList = (1..7 - lastDay.dayOfWeek.ordinal).map {
            LocalDate(nextMonthFirstDay.year, nextMonthFirstDay.month, it)
        }
        nextMonthDayList
    }
    val calendarInfo: List<LocalDate> = previousDateList + currentDateList + nextDateList
    val calendarWeekInfo: List<LocalDate> = calendarInfo.chunked(7).find {
        it.contains(selectedDate)
    } ?: calendarInfo.take(7)
    val showingCalendarInfo: List<LocalDate> = if (isExpanded) {
        calendarInfo
    } else {
        calendarWeekInfo
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOf("일", "월", "화", "수", "목", "금", "토").forEach {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    style = Body1.merge(Gray700)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    showingCalendarInfo.chunked(7).forEachIndexed { index, localDates ->
        if (index > 0) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            localDates.forEach { item ->
                if (item.month.number == showingDate.month.number) {
                    ScheduleScreenHeaderCalendarItem(
                        modifier = Modifier.weight(1f),
                        model = model,
                        item = item,
                        selectedDate = selectedDate,
                        isExpanded = isExpanded,
                        onClickDay = onClickDay
                    )
                } else {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ScheduleScreenHeaderCalendarItem(
    modifier: Modifier = Modifier,
    model: ScheduleModel,
    item: LocalDate,
    selectedDate: LocalDate,
    isExpanded: Boolean,
    onClickDay: (LocalDate) -> Unit
) {
    val isSelected = item == selectedDate

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val boxModifier = if (isSelected) {
            Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Primary4)
        } else {
            Modifier.size(28.dp)
        }
        Box(
            modifier = boxModifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = {
                    onClickDay(item)
                }
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.dayOfMonth.toString(),
                style = Body1.merge(
                    color = if (isSelected) Gray000 else Gray600,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier.height(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                model.scheduleList
                    .filter { schedule -> schedule.day == item }
                    .take(3)
                    .forEachIndexed { index, schedule ->
                        val color =
                            Color(HistoryEventType.getEventTypeColor(schedule.event))

                        if (index > 0) {
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
            }
        }
    }
}

@Preview
@Composable
private fun ScheduleScreenHeaderPreview() {
    ScheduleScreenHeader(
        appState = rememberApplicationState(),
        model = ScheduleModel(
            state = ScheduleState.Init,
            scheduleList = listOf(
                Schedule(
                    id = 4830,
                    relation = ScheduleRelation(
                        id = 7220,
                        name = "Marietta Justice",
                        group = ScheduleRelationGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "결혼",
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = "엄청 긴 메모입니다. 엄청 긴 메모입니다. 엄청 긴 메모입니다. 엄청 긴 메모입니다."
                ),
                Schedule(
                    id = 4830,
                    relation = ScheduleRelation(
                        id = 7220,
                        name = "Marietta Justice",
                        group = ScheduleRelationGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "아무거나",
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = "메모입니다."
                ),
                Schedule(
                    id = 4830,
                    relation = ScheduleRelation(
                        id = 7220,
                        name = "Marietta Justice",
                        group = ScheduleRelationGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "생일",
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = ""
                ),
                Schedule(
                    id = 4830,
                    relation = ScheduleRelation(
                        id = 7220,
                        name = "Marietta Justice",
                        group = ScheduleRelationGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "돌잔치",
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = ""
                )
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        showingDate = LocalDate(2024, 2, 25),
        selectedDate = LocalDate(2024, 2, 1),
        onClickDate = {},
        onClickDay = {}
    )
}
