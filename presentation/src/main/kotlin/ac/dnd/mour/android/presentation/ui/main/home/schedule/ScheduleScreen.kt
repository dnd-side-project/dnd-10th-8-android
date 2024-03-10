package ac.dnd.mour.android.presentation.ui.main.home.schedule

import ac.dnd.mour.android.domain.model.feature.relation.RelationSimple
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimpleGroup
import ac.dnd.mour.android.domain.model.feature.schedule.Schedule
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.makeRoute
import ac.dnd.mour.android.presentation.common.view.calendar.CalendarPicker
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.common.notification.NotificationConstant
import ac.dnd.mour.android.presentation.ui.main.home.schedule.add.ScheduleAddConstant
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn

@Composable
fun ScheduleScreen(
    appState: ApplicationState,
    viewModel: ScheduleViewModel = hiltViewModel()
) {

    val model: ScheduleModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val scheduleList by viewModel.scheduleList.collectAsStateWithLifecycle()

        ScheduleModel(
            state = state,
            scheduleList = scheduleList
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
    val context = LocalContext.current

    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    var showingDate: LocalDate by remember { mutableStateOf(now) }
    var selectedDate: LocalDate by remember { mutableStateOf(now) }

    var isDatePickerShowing: Boolean by remember { mutableStateOf(false) }

    val scheduleList = model.scheduleList.filter { it.day == selectedDate }
    val formattedTitle = "${selectedDate.month.number}월 ${selectedDate.dayOfMonth}일"

    val formattedDate = Unit.let {
        val year = showingDate.year
        val month = showingDate.month.number

        "${year}년 ${month}월"
    }


    fun navigateToScheduleAdd() {
        val route = makeRoute(
            ScheduleAddConstant.ROUTE,
            listOf(
                ScheduleAddConstant.ROUTE_ARGUMENT_SCHEDULE_YEAR to selectedDate.year,
                ScheduleAddConstant.ROUTE_ARGUMENT_SCHEDULE_MONTH to selectedDate.monthNumber,
                ScheduleAddConstant.ROUTE_ARGUMENT_SCHEDULE_DAY to selectedDate.dayOfMonth
            )
        )
        appState.navController.navigate(route)
    }

    fun navigateToNotification() {
        appState.navController.navigate(NotificationConstant.ROUTE)
    }

    fun navigateToInvitation(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(context, browserIntent, null)
    }

    fun navigateToScheduleEdit(id: Long) {
        val route = makeRoute(
            ScheduleAddConstant.ROUTE,
            listOf(ScheduleAddConstant.ROUTE_ARGUMENT_SCHEDULE_ID to id)
        )
        appState.navController.navigate(route)
    }


    if (isDatePickerShowing) {
        CalendarPicker(
            localDate = showingDate,
            isDaySelectable = false,
            onDismissRequest = { isDatePickerShowing = false },
            onConfirm = {
                showingDate = it
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray200)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(Gray000)
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "일정",
                    style = Headline1,
                    modifier = Modifier.align(Alignment.Center)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = {
                                navigateToNotification()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_notification),
                        contentDescription = null
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Gray000)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .clickable {
                            isDatePickerShowing = true
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = formattedDate,
                        style = Headline1.merge(Gray900)
                    )
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_drop_down),
                        contentDescription = null,
                        tint = Gray900
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    ScheduleScreenHeader(
                        appState = appState,
                        model = model,
                        event = event,
                        intent = intent,
                        handler = handler,
                        showingDate = showingDate,
                        selectedDate = selectedDate,
                        onClickDay = {
                            selectedDate = it
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = formattedTitle,
                        style = Headline3,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }
                if (scheduleList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(103.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.weight(7f))
                                Text(
                                    text = "아직 작성한 일정이 없어요",
                                    fontWeight = FontWeight.SemiBold,
                                    style = Body1.merge(Gray700),
                                    letterSpacing = (-0.25).sp,

                                    )
                                Spacer(modifier = Modifier.weight(6f))
                                Text(
                                    text = "일정을 등록하고 미리 알림 받아보세요",
                                    fontWeight = FontWeight.Normal,
                                    style = Body2.merge(Gray600),
                                    letterSpacing = (-0.25).sp
                                )
                                Spacer(modifier = Modifier.weight(24f))
                                Box(
                                    modifier = Modifier
                                        .clip(Shapes.medium)
                                        .background(color = Gray000)
                                        .clickable {
                                            navigateToScheduleAdd()
                                        }
                                        .border(
                                            width = 1.dp,
                                            color = Gray500,
                                            shape = Shapes.medium
                                        )
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 6.5.dp
                                        )
                                ) {
                                    Text(
                                        text = "일정 등록하기",
                                        fontWeight = FontWeight.SemiBold,
                                        style = Body1.merge(color = Gray600),
                                        letterSpacing = (-0.25).sp
                                    )
                                }
                            }
                        }
                    }
                } else {
                    items(scheduleList) { schedule ->
                        Column(
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            ScheduleScreenItem(
                                schedule = schedule,
                                onClickSchedule = {
                                    navigateToScheduleEdit(
                                        id = schedule.id
                                    )
                                },
                                onClickInvitation = {
                                    navigateToInvitation(
                                        link = schedule.link
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(bottom = 24.dp, end = 20.dp)
                .size(52.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = Gray800,
            onClick = {
                navigateToScheduleAdd()
            }
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = null,
                tint = Gray000
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }

    LaunchedEffectWithLifecycle(context = handler) {
        intent(ScheduleIntent.ChangeDate(selectedDate))
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview1() {
    ScheduleScreen(
        appState = rememberApplicationState(),
        model = ScheduleModel(
            state = ScheduleState.Init,
            scheduleList = listOf(
                Schedule(
                    id = 4830,
                    relation = RelationSimple(
                        id = 7220,
                        name = "Marietta Justice",
                        group = RelationSimpleGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "결혼",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = "엄청 긴 메모입니다. 엄청 긴 메모입니다. 엄청 긴 메모입니다. 엄청 긴 메모입니다."
                ),
                Schedule(
                    id = 4830,
                    relation = RelationSimple(
                        id = 7220,
                        name = "Marietta Justice",
                        group = RelationSimpleGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "아무거나",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = "메모입니다."
                ),
                Schedule(
                    id = 4830,
                    relation = RelationSimple(
                        id = 7220,
                        name = "Marietta Justice",
                        group = RelationSimpleGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "생일",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = ""
                ),
                Schedule(
                    id = 4830,
                    relation = RelationSimple(
                        id = 7220,
                        name = "Marietta Justice",
                        group = RelationSimpleGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "돌잔치",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "graeco",
                    location = "aliquet",
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
private fun ScheduleScreenPreview2() {
    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    ScheduleScreen(
        appState = rememberApplicationState(),
        model = ScheduleModel(
            state = ScheduleState.Init,
            scheduleList = listOf(
                Schedule(
                    id = 4830,
                    relation = RelationSimple(
                        id = 7220,
                        name = "Marietta Justice",
                        group = RelationSimpleGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = now,
                    event = "결혼",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = "엄청 긴 메모입니다. 엄청 긴 메모입니다. 엄청 긴 메모입니다. 엄청 긴 메모입니다."
                ),
                Schedule(
                    id = 4830,
                    relation = RelationSimple(
                        id = 7220,
                        name = "Marietta Justice",
                        group = RelationSimpleGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = now,
                    event = "아무거나",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = "메모입니다."
                ),
                Schedule(
                    id = 4830,
                    relation = RelationSimple(
                        id = 7220,
                        name = "Marietta Justice",
                        group = RelationSimpleGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = now,
                    event = "생일",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = ""
                ),
                Schedule(
                    id = 4830,
                    relation = RelationSimple(
                        id = 7220,
                        name = "Marietta Justice",
                        group = RelationSimpleGroup(
                            id = 2824,
                            name = "Allen O'Neil"
                        )
                    ),
                    day = now,
                    event = "돌잔치",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "graeco",
                    location = "aliquet",
                    memo = ""
                )
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
