package ac.dnd.mour.android.presentation.ui.main.home.history.registration

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space16
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.theme.Space24
import ac.dnd.mour.android.presentation.common.theme.Space4
import ac.dnd.mour.android.presentation.common.theme.Space56
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.mour.android.presentation.common.util.logevent.LogEventUtil
import ac.dnd.mour.android.presentation.common.util.logevent.viewLogEvent
import ac.dnd.mour.android.presentation.common.view.SnackBarScreen
import ac.dnd.mour.android.presentation.common.view.calendar.CalendarConfig
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
import ac.dnd.mour.android.presentation.model.history.HistoryEventType
import ac.dnd.mour.android.presentation.model.history.HistoryRegistrationType
import ac.dnd.mour.android.presentation.model.history.HistoryTagType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.common.calendar.HistoryCalendarScreen
import ac.dnd.mour.android.presentation.ui.main.home.HomeConstant
import ac.dnd.mour.android.presentation.ui.main.home.common.relation.get.SearchRelationScreen
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryRegistrationScreen(
    appState: ApplicationState,
    model: HistoryRegistrationModel,
    event: EventFlow<HistoryRegistrationEvent>,
    intent: (HistoryRegistrationIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    calendarConfig: CalendarConfig = CalendarConfig(),
    id: Long,
    name: String,
    isHome: Boolean
) {
    appState.setStatusBarColor(Gray000)

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    var isContinuousState by rememberSaveable { mutableStateOf(false) }
    var historyTypeState by rememberSaveable { mutableStateOf(HistoryRegistrationType.TAKE) }
    var priceText by rememberSaveable { mutableStateOf("") }
    var userNameText by rememberSaveable { mutableStateOf(name) }
    var relationId by rememberSaveable { mutableLongStateOf(id) }
    var selectedYear by rememberSaveable { mutableIntStateOf(calendarConfig.getCalendarYear()) }
    var selectedMonth by rememberSaveable { mutableIntStateOf(calendarConfig.getCalendarMonth()) }
    var selectedDay by rememberSaveable { mutableIntStateOf(calendarConfig.getCalendarDay()) }
    var eventTypeText by rememberSaveable { mutableStateOf("") }
    var selectedEventId by rememberSaveable { mutableLongStateOf(-1) }
    var memoText by rememberSaveable { mutableStateOf("") }
    val tagIdList = remember { mutableStateListOf<Long>() }

    var isCalendarShowingState by rememberSaveable { mutableStateOf(false) }
    var isAddNameShowingState by rememberSaveable { mutableStateOf(false) }

    var isViewUnRecordMessage by remember { mutableStateOf("") }

    val typePositionState = animateDpAsState(
        targetValue = if (historyTypeState == HistoryRegistrationType.TAKE) 0.dp else 106.dp,
        label = "type background position "
    )

    @Composable
    fun getTypeTextColor(currentType: HistoryRegistrationType) = animateColorAsState(
        targetValue = if (historyTypeState == currentType) Gray900 else Gray700,
        label = "type text color"
    )

    LaunchedEffect(historyTypeState){
        when(historyTypeState){
            HistoryRegistrationType.TAKE -> {
                viewLogEvent(
                    LogEventUtil.VIEW_MONEY_TAKE,
                    LogEventUtil.VIEW_MONEY_TAKE
                )
            }
            HistoryRegistrationType.GIVE -> {
                viewLogEvent(
                    LogEventUtil.VIEW_MONEY_GIVE,
                    LogEventUtil.VIEW_MONEY_GIVE
                )
            }
        }
    }

    fun navigateToStep() {
        if (isContinuousState) {
            focusManager.clearFocus()
            historyTypeState = HistoryRegistrationType.TAKE
            priceText = ""
            userNameText = "이름 선택"
            relationId = -1
            selectedYear = calendarConfig.getCalendarYear()
            selectedMonth = calendarConfig.getCalendarMonth()
            selectedDay = calendarConfig.getCalendarDay()
            eventTypeText = ""
            selectedEventId = -1
            memoText = ""
            tagIdList.clear()
        } else {
            if (isHome) {
                appState.navController.navigate(HomeConstant.ROUTE)
            } else {
                appState.navController.popBackStack()
            }
        }
    }

    fun register(continuousState: Boolean) {
        isContinuousState = continuousState
        intent(
            HistoryRegistrationIntent.OnClickSubmit(
                relationId = relationId,
                money = priceText.toLongOrNull() ?: 0L,
                give = HistoryRegistrationType.getHistoryRegistration(
                    historyTypeState
                ),
                day = LocalDate(
                    selectedYear,
                    selectedMonth,
                    selectedDay
                ),
                event = eventTypeText,
                memo = memoText,
                tags = HistoryTagType.getTagNameList(tagIdList)
            )
        )
    }

    fun submit(event: HistoryRegistrationEvent.Submit) {
        when (event) {
            is HistoryRegistrationEvent.Submit.Success -> navigateToStep()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray000)
            .addFocusCleaner(focusManager)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(Space56)
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 13.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier.clickable {
                    appState.navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.width(Space4))
            Text(
                text = "마음 등록하기",
                style = Headline1.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {
            Column(
                modifier = Modifier
                    .padding(top = Space56)
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState)
                    .padding(horizontal = Space20)
            ) {
                Spacer(modifier = Modifier.height(Space16))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Gray200,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .width(214.dp)
                            .height(38.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .padding(start = typePositionState.value)
                                .clip(RoundedCornerShape(100.dp))
                                .width(104.dp)
                                .height(34.dp)
                                .background(color = Gray000),
                        )
                        HistoryRegistrationType.entries.forEach { registrationType ->
                            Box(
                                modifier = Modifier
                                    .align(
                                        when (registrationType) {
                                            HistoryRegistrationType.TAKE -> Alignment.CenterStart
                                            HistoryRegistrationType.GIVE -> Alignment.CenterEnd
                                        }
                                    )
                                    .padding(2.dp)
                                    .width(104.dp)
                                    .height(34.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .clickable {
                                        historyTypeState = registrationType
                                    }
                                    .background(color = Color.Transparent),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = registrationType.typeName,
                                    style = Body1.merge(
                                        color = getTypeTextColor(registrationType).value,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space20))

                TypingPriceField(
                    modifier = Modifier.fillMaxWidth(),
                    textValue = priceText,
                    onValueChange = {
                        priceText = it
                    },
                    hintText = "금액을 입력해주세요"
                )
                Spacer(modifier = Modifier.height(Space24))

                FieldSubject("이름")
                Spacer(modifier = Modifier.height(6.dp))
                FieldSelectComponent(
                    isSelected = isAddNameShowingState,
                    text = userNameText,
                    onClick = {
                        isAddNameShowingState = true
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                FieldSubject("날짜")
                Spacer(modifier = Modifier.height(6.dp))
                FieldSelectComponent(
                    isSelected = isCalendarShowingState,
                    text = listOf(selectedYear, selectedMonth, selectedDay).joinToString(" / "),
                    onClick = {
                        isCalendarShowingState = true
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                FieldSubject("경사 종류")
                Spacer(modifier = Modifier.height(6.dp))
                TypingTextField(
                    textType = TypingTextFieldType.Basic,
                    text = eventTypeText,
                    onValueChange = {
                        selectedEventId = HistoryEventType.getEventId(it)
                        eventTypeText = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    hintText = "직접 입력",
                    trailingIconContent = {
                        if (eventTypeText.isNotEmpty()) {
                            Image(
                                painter = painterResource(R.drawable.ic_close_circle),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        eventTypeText = ""
                                    }
                            )
                        }
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                HistoryEventType.entries.chunked(5).forEach { registrationEventTypes ->
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        registrationEventTypes.forEach { type ->
                            ChipItem(
                                chipType = ChipType.BORDER,
                                chipText = type.eventName,
                                currentSelectedId = setOf(selectedEventId),
                                chipId = type.id,
                                onSelectChip = { selectId ->
                                    selectedEventId =
                                        if (selectedEventId == selectId) -1 else selectId
                                    eventTypeText =
                                        HistoryEventType.getEventName(selectId)
                                },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
                Spacer(modifier = Modifier.height(18.dp))

                FieldSubject(
                    subject = "메모",
                    isViewIcon = false
                )
                Spacer(modifier = Modifier.height(6.dp))
                TypingTextField(
                    textType = TypingTextFieldType.LongSentence,
                    text = memoText,
                    onValueChange = {
                        memoText = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    hintText = "경사 관련 메모를 입력해주세요",
                )
                Spacer(modifier = Modifier.height(24.dp))

                FieldSubject("태그", isViewIcon = false)
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
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        Row(
            modifier = Modifier
                .background(color = Gray000)
                .align(Alignment.BottomCenter)
                .padding(
                    vertical = Space12,
                    horizontal = Space20
                ),
            horizontalArrangement = Arrangement.spacedBy(Space12)
        ) {
            ConfirmButton(
                modifier = Modifier.weight(1f),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Secondary
                ),
                onClick = {
                    isViewUnRecordMessage = checkRegistrable(
                        relationId = relationId,
                        money = priceText.toLongOrNull() ?: 0L,
                        event = eventTypeText
                    )
                    if (isViewUnRecordMessage.isEmpty() && model.state == HistoryRegistrationState.Init) {
                        when(historyTypeState){
                            HistoryRegistrationType.TAKE -> {
                                viewLogEvent(
                                    LogEventUtil.VIEW_MONEY_TAKE,
                                    LogEventUtil.CLICK_NEXT_SAVE_TAKE
                                )
                            }
                            HistoryRegistrationType.GIVE -> {
                                viewLogEvent(
                                    LogEventUtil.VIEW_MONEY_GIVE,
                                    LogEventUtil.CLICK_NEXT_SAVE_GIVE
                                )
                            }
                        }
                        register(continuousState = true)
                    }
                },
                content = {
                    Text(
                        text = "연속저장",
                        style = Headline3.merge(
                            color = Gray700,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            )
            ConfirmButton(
                modifier = Modifier.weight(1f),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                onClick = {
                    isViewUnRecordMessage = checkRegistrable(
                        relationId = relationId,
                        money = priceText.toLongOrNull() ?: 0L,
                        event = eventTypeText
                    )
                    if (isViewUnRecordMessage.isEmpty() && model.state == HistoryRegistrationState.Init) {
                        when(historyTypeState){
                            HistoryRegistrationType.TAKE -> {
                                viewLogEvent(
                                    LogEventUtil.VIEW_MONEY_TAKE,
                                    LogEventUtil.CLICK_SAVE_TAKE
                                )
                            }
                            HistoryRegistrationType.GIVE -> {
                                viewLogEvent(
                                    LogEventUtil.VIEW_MONEY_GIVE,
                                    LogEventUtil.CLICK_SAVE_GIVE
                                )
                            }
                        }
                        register(continuousState = false)
                    }
                },
                content = {
                    Text(
                        text = "저장하기",
                        style = Headline3.merge(
                            color = Gray000,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            )
        }

        if (isViewUnRecordMessage.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 65.dp)
            ) {
                SnackBarScreen(isViewUnRecordMessage)
            }
            scope.launch {
                delay(500L)
                isViewUnRecordMessage = ""
            }
        }

        if (isCalendarShowingState) {
            HistoryCalendarScreen(
                calendarConfig = calendarConfig,
                selectedYear = selectedYear,
                selectedMonth = selectedMonth,
                selectedDay = selectedDay,
                onClose = {
                    isCalendarShowingState = false
                },
                onConfirm = { year, month, day ->
                    selectedYear = year
                    selectedMonth = month
                    selectedDay = day
                    isCalendarShowingState = false
                }
            )
        }

        if (isAddNameShowingState) {
            SearchRelationScreen(
                appState = appState,
                onDismissRequest = {
                    isAddNameShowingState = false
                },
                onResult = {
                    userNameText = it.name
                    relationId = it.id
                    isAddNameShowingState = false
                },
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is HistoryRegistrationEvent.Submit -> (submit(event))
            }
        }
    }
}

private fun checkRegistrable(
    relationId: Long,
    money: Long,
    event: String
): String {
    return if (relationId == -1L) {
        "이름이 선택되지 않았습니다."
    } else if (money == 0L) {
        "금액이 입력되지 않았습니다."
    } else if (event.isEmpty()) {
        "경사 종류가 선택되지 않았습니다."
    } else {
        ""
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true,
    apiLevel = 33
)
@Composable
fun HistoryRegistrationScreenPreview() {
    HistoryRegistrationScreen(
        appState = rememberApplicationState(),
        model = HistoryRegistrationModel(
            state = HistoryRegistrationState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        id = -1L,
        name = "",
        isHome = true
    )
}
