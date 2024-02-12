package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelationGroup
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Body2
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline0
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.calendar.CalendarPicker
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.ScheduleAddConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
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

    fun navigateToScheduleAdd() {
        appState.navController.navigate(ScheduleAddConstant.ROUTE)
    }

    fun navigateToNotification() {
        // TODO : navigate to notification
//        appState.navController.navigate(NotificationConstant.ROUTE)
    }

    fun navigateToInvitation(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(context, browserIntent, null)
    }

    fun navigateToScheduleEdit(id: Long) {
        // TODO : schedule edit argument
        appState.navController.navigate(ScheduleAddConstant.ROUTE)
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
                    text = "MUR",
                    style = Headline0.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            navigateToNotification()
                        }
                )
            }
            ScheduleScreenHeader(
                appState = appState,
                model = model,
                event = event,
                intent = intent,
                handler = handler,
                showingDate = showingDate,
                selectedDate = selectedDate,
                onClickDate = {
                    isDatePickerShowing = true
                },
                onClickDay = {
                    selectedDate = it
                }
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(Space20)
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = formattedTitle,
                        style = Headline3
                    )
                }
                if (scheduleList.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "아직 작성한 일정이 없어요",
                                style = Body1.merge(Gray700)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "일정을 등록하고 미리 알림 받아보세요",
                                style = Body2.merge(Gray600)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            ConfirmButton(
                                properties = ConfirmButtonProperties(
                                    size = ConfirmButtonSize.Medium,
                                    type = ConfirmButtonType.Outline
                                ),
                                onClick = {
                                    navigateToScheduleAdd()
                                }
                            ) { style ->
                                Text(
                                    text = "일정 등록하기",
                                    style = style
                                )
                            }
                        }
                    }
                }
                items(scheduleList) { schedule ->
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
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .size(52.dp)
                .padding(bottom = 24.dp, end = 20.dp)
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
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    ScheduleScreen(
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
                    location = "aliquet"
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
                    location = "aliquet"
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
                    location = "aliquet"
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
                    location = "aliquet"
                )
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
