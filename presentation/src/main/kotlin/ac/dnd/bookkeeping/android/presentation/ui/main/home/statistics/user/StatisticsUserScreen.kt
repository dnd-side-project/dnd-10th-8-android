package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user.group.StatisticsUserGroupScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.type.UserGender
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun StatisticsUserScreen(
    appState: ApplicationState
) {

    val viewModel: StatisticsUserViewModel = hiltViewModel()

    val model: StatisticsUserModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        StatisticsUserModel(
            state = state,
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
            state = StatisticsUserState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
