package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user

import ac.dnd.bookkeeping.android.domain.model.feature.statistics.GroupStatistics
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary6
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space16
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.chart.stick.StickChart
import ac.dnd.bookkeeping.android.presentation.common.view.chart.stick.StickChartData
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryEventType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user.group.StatisticsUserGroupScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.type.UserGender
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.random.Random

@Composable
fun StatisticsUserScreen(
    appState: ApplicationState
) {

    val viewModel: StatisticsUserViewModel = hiltViewModel()

    val model: StatisticsUserModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val groupStatistics by viewModel.groupStatistics.collectAsStateWithLifecycle()

        StatisticsUserModel(
            state = state,
            groupStatistics = groupStatistics
        )
    }

    ErrorObserver(viewModel)

    StatisticsUserScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@Composable
private fun StatisticsUserScreen(
    appState: ApplicationState,
    model: StatisticsUserModel,
    event: EventFlow<StatisticsUserEvent>,
    intent: (StatisticsUserIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()

    var isSelectGroupShowing: Boolean by remember { mutableStateOf(false) }
    var age: Int by remember { mutableIntStateOf(20) }
    var gender: UserGender by remember { mutableStateOf(UserGender.Female) }

    val formattedGroupName = Unit.let {
        val formattedGender = when (gender) {
            UserGender.Female -> "여성"
            UserGender.Male -> "남성"
        }
        "${age}대 $formattedGender"
    }

    val statisticsData: Map<Long, List<GroupStatistics>> = model.groupStatistics.sortedBy {
        HistoryEventType.getEventId(it.event).takeIf { it != -1L } ?: Long.MAX_VALUE
    }.groupBy { item ->
        HistoryEventType.getEventId(item.event)
    }

    val chartData: List<StickChartData> = statisticsData.map { (key, item) ->
        StickChartData(
            color = Color(HistoryEventType.getEventTypeColor(item.first().event)),
            money = item.sumOf { it.amount }.toInt()
        )
    }

    fun onChangeGroup() {
        intent(StatisticsUserIntent.OnChangeGroup(age, gender))
    }

    if (isSelectGroupShowing) {
        StatisticsUserGroupScreen(
            appState = appState,
            onDismissRequest = { isSelectGroupShowing = false },
            onResult = { resultAge, resultGender ->
                age = resultAge
                gender = resultGender
                onChangeGroup()
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = Shapes.medium,
                backgroundColor = Primary1,
                elevation = 0.dp
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .clickable {
                            isSelectGroupShowing = true
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formattedGroupName,
                            style = Headline3.merge(Primary6)
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_chevron_down),
                            contentDescription = null,
                            modifier = Modifier.size(Space16),
                            tint = Primary6
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                text = "경사별 평균 경사비",
                style = Headline2
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "경사에 한번 참여했을 때 \n얼마를 지출하고 있는지 확인해보세요",
            style = Body1.merge(Gray700)
        )
        Spacer(modifier = Modifier.height(80.dp))
        StickChart(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            dataList = chartData,
            thickness = 0.5f
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
private fun StatisticsUserScreenPreview() {
    StatisticsUserScreen(
        appState = rememberApplicationState(),
        model = StatisticsUserModel(
            state = StatisticsUserState.Init,
            groupStatistics = listOf(
                GroupStatistics(
                    event = "결혼",
                    amount = Random.nextLong(50_000, 200_000)
                ),
                GroupStatistics(
                    event = "생일",
                    amount = Random.nextLong(50_000, 400_000)
                ),
                GroupStatistics(
                    event = "돌잔치",
                    amount = Random.nextLong(0, 50_000)
                ),
                GroupStatistics(
                    event = "출산",
                    amount = Random.nextLong(0, 50_000)
                ),
                GroupStatistics(
                    event = "개업",
                    amount = Random.nextLong(40_000, 300_000)
                ),
                GroupStatistics(
                    event = "랜덤이벤트1",
                    amount = Random.nextLong(0, 500_000)
                ),
                GroupStatistics(
                    event = "랜덤이벤트2",
                    amount = Random.nextLong(50_000, 100_000)
                ),
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
