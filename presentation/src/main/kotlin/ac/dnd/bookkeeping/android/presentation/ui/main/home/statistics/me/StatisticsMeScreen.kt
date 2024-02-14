package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me

import ac.dnd.bookkeeping.android.common.ifZero
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatisticsItem
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline0
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Icon24
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary2
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.makeRoute
import ac.dnd.bookkeeping.android.presentation.common.view.calendar.CalendarPicker
import ac.dnd.bookkeeping.android.presentation.common.view.chart.pie.PieChart
import ac.dnd.bookkeeping.android.presentation.common.view.chart.pie.PieChartData
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipItem
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipType
import ac.dnd.bookkeeping.android.presentation.common.view.segment.SegmentControl
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryEventType
import ac.dnd.bookkeeping.android.presentation.model.statistics.MyStatisticsChipType
import ac.dnd.bookkeeping.android.presentation.model.statistics.MyStatisticsSegmentType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.event.StatisticsMeEventConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

@Composable
fun StatisticsMeScreen(
    appState: ApplicationState
) {

    val viewModel: StatisticsMeViewModel = hiltViewModel()

    val model: StatisticsMeModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val myStatistics by viewModel.myStatistics.collectAsStateWithLifecycle()

        StatisticsMeModel(
            state = state,
            myStatistics = myStatistics
        )
    }

    ErrorObserver(viewModel)

    StatisticsMeScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@Composable
private fun StatisticsMeScreen(
    appState: ApplicationState,
    model: StatisticsMeModel,
    event: EventFlow<StatisticsMeEvent>,
    intent: (StatisticsMeIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()

    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val segmentItemList: List<MyStatisticsSegmentType> = listOf(
        MyStatisticsSegmentType.Monthly,
        MyStatisticsSegmentType.Yearly
    )
    val chipItemList: List<MyStatisticsChipType> = listOf(
        MyStatisticsChipType.Take,
        MyStatisticsChipType.Give
    )

    var selectedSegmentType: MyStatisticsSegmentType by remember {
        mutableStateOf(
            MyStatisticsSegmentType.Monthly
        )
    }
    var selectedChipType: MyStatisticsChipType by remember { mutableStateOf(MyStatisticsChipType.Take) }
    var date: LocalDate by remember { mutableStateOf(now) }

    var isDatePickerShowing: Boolean by remember { mutableStateOf(false) }

    val formattedDate: String = when (selectedSegmentType) {
        MyStatisticsSegmentType.Monthly -> {
            "${date.month.number}월"
        }

        MyStatisticsSegmentType.Yearly -> {
            "${date.year}년"
        }
    }
    val takeSum = model.myStatistics.take.sumOf { it.money }
    val giveSum = model.myStatistics.give.sumOf { it.money }
    val formattedTakeSum = Unit.let {
        val sum = takeSum.toString()
        val length = sum.length
        val formatted = StringBuilder()
        for (i in 0 until length) {
            formatted.append(sum[i])
            if ((length - i - 1) % 3 == 0 && i != length - 1) {
                formatted.append(",")
            }
        }
        "${formatted}원"
    }
    val formattedGiveSum = Unit.let {
        val sum = giveSum.toString()
        val length = sum.length
        val formatted = StringBuilder()
        for (i in 0 until length) {
            formatted.append(sum[i])
            if ((length - i - 1) % 3 == 0 && i != length - 1) {
                formatted.append(",")
            }
        }
        "${formatted}원"
    }
    val statisticsData: Map<Long, List<MyStatisticsItem>> = when (selectedChipType) {
        MyStatisticsChipType.Take -> model.myStatistics.take
        MyStatisticsChipType.Give -> model.myStatistics.give
    }.sortedBy {
        HistoryEventType.getEventId(it.event).ifZero { Long.MAX_VALUE }
    }.groupBy { item ->
        HistoryEventType.getEventId(item.event)
    }

    val chartData: List<PieChartData> = statisticsData.map { (key, item) ->
        PieChartData(
            color = Color(HistoryEventType.getEventTypeColor(item.first().event)),
            value = item.sumOf { it.money }.toInt()
        )
    }

    fun onClickDateChange() {
        intent(
            StatisticsMeIntent.OnClickDateChange(
                year = date.year,
                month = if (selectedSegmentType == MyStatisticsSegmentType.Monthly) {
                    date.month.number
                } else {
                    0
                }
            )
        )
    }

    fun navigateToStatisticsMeEvent(
        id: Long
    ) {
        val route = makeRoute(
            StatisticsMeEventConstant.ROUTE,
            listOf(StatisticsMeEventConstant.ROUTE_ARGUMENT_EVENT_ID to id)
        )
        appState.navController.navigate(route)
    }

    if (isDatePickerShowing) {
        CalendarPicker(
            localDate = date,
            isDaySelectable = false,
            onDismissRequest = {
                isDatePickerShowing = false
            },
            onConfirm = {
                date = it
            }
        )
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        SegmentControl<MyStatisticsSegmentType>(
            modifier = Modifier.fillMaxWidth(),
            segments = segmentItemList,
            selectedSegment = selectedSegmentType,
            onSegmentSelected = { segment ->
                selectedSegmentType = segment
                date = now
                selectedChipType = MyStatisticsChipType.Take
                onClickDateChange()
            }
        ) { segment ->
            val text = when (segment) {
                MyStatisticsSegmentType.Monthly -> "월별"
                MyStatisticsSegmentType.Yearly -> "연도별"
            }
            Text(
                text = text,
                style = Body1
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = Shapes.medium,
            backgroundColor = Primary4,
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = {
                            date = when (selectedSegmentType) {
                                MyStatisticsSegmentType.Monthly -> {
                                    date.minus(1, DateTimeUnit.MONTH)
                                }

                                MyStatisticsSegmentType.Yearly -> {
                                    date.minus(1, DateTimeUnit.YEAR)
                                }
                            }
                            onClickDateChange()
                        }
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(Icon24),
                        painter = painterResource(id = R.drawable.ic_drop_left),
                        contentDescription = null,
                        tint = Gray000
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            isDatePickerShowing = true
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = formattedDate,
                        style = when (selectedSegmentType) {
                            MyStatisticsSegmentType.Monthly -> Headline0.merge(color = Gray000)
                            MyStatisticsSegmentType.Yearly -> Headline1.merge(color = Gray000)
                        }
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = {
                            date = when (selectedSegmentType) {
                                MyStatisticsSegmentType.Monthly -> {
                                    date.plus(1, DateTimeUnit.MONTH)
                                }

                                MyStatisticsSegmentType.Yearly -> {
                                    date.plus(1, DateTimeUnit.YEAR)
                                }
                            }
                            onClickDateChange()
                        }
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(Icon24),
                        painter = painterResource(id = R.drawable.ic_drop_right),
                        contentDescription = null,
                        tint = Gray000
                    )
                }

                Spacer(modifier = Modifier.width(9.dp))
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .height(30.dp),
                    color = Primary2
                )
                Spacer(modifier = Modifier.width(7.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 9.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "받은 마음", style = Body1.merge(Primary1))
                        Text(text = formattedTakeSum, style = Body1.merge(Primary1))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "보낸 마음", style = Body1.merge(Primary1))
                        Text(text = formattedGiveSum, style = Body1.merge(Primary1))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(34.dp))
        Text(text = "경사 별 받은 통계", style = Headline2)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            ChipItem(
                chipType = ChipType.BORDER,
                chipText = "받은 마음",
                chipId = 1,
                currentSelectedId = setOf(
                    if (selectedChipType == MyStatisticsChipType.Take) {
                        1
                    } else {
                        0
                    }
                ),
                onSelectChip = {
                    selectedChipType = MyStatisticsChipType.Take
                }
            )
            Spacer(modifier = Modifier.width(6.dp))
            ChipItem(
                chipType = ChipType.BORDER,
                chipText = "보낸 마음",
                chipId = 2,
                currentSelectedId = setOf(
                    if (selectedChipType == MyStatisticsChipType.Give) {
                        2
                    } else {
                        0
                    }
                ),
                onSelectChip = {
                    selectedChipType = MyStatisticsChipType.Give
                }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        PieChart(
            modifier = Modifier
                .padding(horizontal = 75.dp, vertical = 15.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            dataList = chartData,
            thickness = 0.5f
        )
        Spacer(modifier = Modifier.height(6.dp))
        statisticsData.forEach { (key, item) ->
            val color = Color(HistoryEventType.getEventTypeColor(item.first().event))
            val title = HistoryEventType.getEventName(key).ifEmpty { "기타" }
            val money = item.sumOf { it.money }
            val percent = money.toFloat() / when (selectedChipType) {
                MyStatisticsChipType.Take -> model.myStatistics.take.sumOf { it.money }
                MyStatisticsChipType.Give -> model.myStatistics.give.sumOf { it.money }
            }
            StatisticsMeScreenItem(
                color = color,
                title = title,
                percent = percent,
                money = money,
                onClick = {
                    // TODO
                }
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun StatisticsMeScreenItem(
    color: Color,
    title: String,
    @FloatRange(from = 0.0, to = 1.0) percent: Float,
    money: Long,
    onClick: () -> Unit
) {
    val formattedPercent = "${(percent * 100).toInt()}%"
    val formattedMoney = Unit.let {
        val sum = money.toString()
        val length = sum.length
        val formatted = StringBuilder()
        for (i in 0 until length) {
            formatted.append(sum[i])
            if ((length - i - 1) % 3 == 0 && i != length - 1) {
                formatted.append(",")
            }
        }
        "${formatted}원"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(12.dp)
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                text = title,
                style = Body1
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = formattedPercent,
                style = Body1.merge(color = Gray600)
            )
            Text(
                text = formattedMoney,
                style = Body1
            )
            Icon(
                modifier = Modifier.size(Icon24),
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = Gray500
            )
        }
    }
}

@Preview
@Composable
private fun StatisticsMeScreenPreview() {
    StatisticsMeScreen(
        appState = rememberApplicationState(),
        model = StatisticsMeModel(
            state = StatisticsMeState.Init,
            myStatistics = MyStatistics(
                give = listOf(
                    MyStatisticsItem(
                        event = "결혼",
                        relationName = "친구",
                        groupName = "veri",
                        money = 4162,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "생일",
                        relationName = "가족",
                        groupName = "antica",
                        money = 9402,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "그외",
                        relationName = "가족",
                        groupName = "anticas",
                        money = 14022,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                ),
                take = listOf(
                    MyStatisticsItem(
                        event = "결혼",
                        relationName = "친구",
                        groupName = "veri",
                        money = 4162,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "생일",
                        relationName = "가족",
                        groupName = "antica",
                        money = 9402,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "그외",
                        relationName = "가족",
                        groupName = "anticas",
                        money = 14022,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                )
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
