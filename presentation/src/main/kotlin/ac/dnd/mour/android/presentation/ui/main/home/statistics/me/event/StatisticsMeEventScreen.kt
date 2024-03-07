package ac.dnd.mour.android.presentation.ui.main.home.statistics.me.event

import ac.dnd.mour.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.mour.android.domain.model.feature.statistics.MyStatisticsItem
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Caption1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline0
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Icon24
import ac.dnd.mour.android.presentation.common.theme.Primary5
import ac.dnd.mour.android.presentation.common.theme.Secondary6
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.chip.ChipItem
import ac.dnd.mour.android.presentation.common.view.chip.ChipType
import ac.dnd.mour.android.presentation.model.history.HistoryEventType
import ac.dnd.mour.android.presentation.model.relation.DefaultGroupType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.datetime.LocalDate
import kotlin.random.Random

@Composable
fun StatisticsMeEventScreen(
    appState: ApplicationState,
    model: StatisticsMeEventModel,
    event: EventFlow<StatisticsMeEventEvent>,
    intent: (StatisticsMeEventIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()

    var selectedEventId: Long by remember { mutableLongStateOf(model.initialEventId) }

    val data = if (model.isGive) {
        model.myStatistics.give
    } else {
        model.myStatistics.take
    }
    val chipData: Map<Long, List<MyStatisticsItem>> = data.sortedBy {
        HistoryEventType.getEventId(it.event).takeIf { it != -1L } ?: Long.MAX_VALUE
    }.groupBy { item ->
        HistoryEventType.getEventId(item.event)
    }
    val listData: List<MyStatisticsItem> = chipData[selectedEventId].orEmpty()

    val formattedTitle: String = if (model.isGive) {
        "보낸 마음"
    } else {
        "받은 마음"
    }
    val formattedMoney = listData.sumOf {
        it.money
    }.toString().let {
        if (it.length > 4) {
            "${it.substring(0, it.length - 4)}만원"
        } else {
            "${it}원"
        }
    }
    val formattedHistoryCount = "내역 ${listData.size}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray200)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = {
                        appState.navController.popBackStack()
                    }
                )
            ) {
                Icon(
                    modifier = Modifier.size(Icon24),
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "경사 별 금액 통계",
                style = Headline1
            )
        }
        if (chipData.isNotEmpty()) {
            Spacer(modifier = Modifier.height(28.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(chipData.size) { index ->
                    val key = chipData.keys.elementAt(index)
                    val item = chipData.values.elementAt(index)
                    val title = HistoryEventType.getEventName(key).ifEmpty { "기타" }
                    ChipItem(
                        chipType = ChipType.MAIN,
                        currentSelectedId = setOf(selectedEventId),
                        chipText = title,
                        onSelectChip = {
                            selectedEventId = it
                        },
                        chipId = key
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(26.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = formattedTitle,
            style = Body1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = formattedMoney,
                style = Headline0
            )
            Text(
                text = formattedHistoryCount,
                style = Body1.merge(Gray700)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (listData.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "아직 받은 내역이 없어요",
                    style = Body1.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .weight(1f),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listData.size) { index ->
                    val item = listData[index]
                    StatisticsMeEventItemScreen(
                        item = item,
                        isGive = model.isGive
                    )
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
private fun StatisticsMeEventItemScreen(
    item: MyStatisticsItem,
    isGive: Boolean
) {
    val iconRes = DefaultGroupType.getGroupResource(item.groupName)
    val formattedIsGive = if (isGive) {
        "보냄"
    } else {
        "받음"
    }
    val formattedMoney = Unit.let {
        val text = item.money.toString()
        val length = text.length
        val formatted = StringBuilder()
        for (i in 0 until length) {
            formatted.append(text[i])
            if ((length - i - 1) % 3 == 0 && i != length - 1) {
                formatted.append(",")
            }
        }
        if (isGive) {
            "-${formatted}원"
        } else {
            "${formatted}원"
        }
    }

    Card(
        shape = Shapes.medium,
        backgroundColor = Gray000,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(Icon24),
                    painter = painterResource(id = iconRes),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = item.relationName,
                        style = Headline3
                    )
                    Text(
                        text = item.groupName,
                        style = Body1.merge(Gray600)
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = formattedIsGive,
                    style = Body1.merge(Gray600)
                )
                Text(
                    text = formattedMoney,
                    style = Body1.merge(
                        if (isGive) {
                            Secondary6
                        } else {
                            Primary5
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            if (HistoryEventType.getEventId(item.event) == -1L) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        text = item.event,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = Caption1.merge(Gray800)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StatisticsMeEventScreenPreview1() {
    StatisticsMeEventScreen(
        appState = rememberApplicationState(),
        model = StatisticsMeEventModel(
            state = StatisticsMeEventState.Init,
            myStatistics = MyStatistics(
                give = listOf(
                    MyStatisticsItem(
                        event = "결혼",
                        relationName = "이다빈",
                        groupName = "친구",
                        money = Random.nextLong(20_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "생일",
                        relationName = "서지원",
                        groupName = "가족",
                        money = Random.nextLong(20_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "그외",
                        relationName = "김경민",
                        groupName = "가족",
                        money = Random.nextLong(50_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "가나다라마바사아자차카타파하"
                    ),
                ),
                take = listOf(
                    MyStatisticsItem(
                        event = "결혼",
                        relationName = "박예리나",
                        groupName = "친구",
                        money = Random.nextLong(20_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "생일",
                        relationName = "김진우",
                        groupName = "가족",
                        money = Random.nextLong(20_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "그외",
                        relationName = "장성혁",
                        groupName = "가족",
                        money = Random.nextLong(50_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                )
            ),
            initialEventId = -1L,
            isGive = true
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}

@Preview
@Composable
private fun StatisticsMeEventScreenPreview2() {
    StatisticsMeEventScreen(
        appState = rememberApplicationState(),
        model = StatisticsMeEventModel(
            state = StatisticsMeEventState.Init,
            myStatistics = MyStatistics.empty,
            initialEventId = -1L,
            isGive = true
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
