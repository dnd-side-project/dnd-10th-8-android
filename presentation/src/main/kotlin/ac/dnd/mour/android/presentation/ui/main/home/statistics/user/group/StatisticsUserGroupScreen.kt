package ac.dnd.mour.android.presentation.ui.main.home.statistics.user.group

import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.BottomSheetScreen
import ac.dnd.mour.android.presentation.common.view.chip.ChipItem
import ac.dnd.mour.android.presentation.common.view.chip.ChipType
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.registration.main.type.UserGender
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun StatisticsUserGroupScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onResult: (age: Int, gender: UserGender) -> Unit,
    viewModel: StatisticsUserGroupViewModel = hiltViewModel()
) {
    val model: StatisticsUserGroupModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        StatisticsUserGroupModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    StatisticsUserGroupScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler,
        onDismissRequest = onDismissRequest,
        onResult = onResult
    )
}

@Composable
private fun StatisticsUserGroupScreen(
    appState: ApplicationState,
    model: StatisticsUserGroupModel,
    event: EventFlow<StatisticsUserGroupEvent>,
    intent: (StatisticsUserGroupIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: (age: Int, gender: UserGender) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var age: Int by remember { mutableIntStateOf(20) }
    var gender: UserGender by remember { mutableStateOf(UserGender.Female) }

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray000)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "그룹 선택",
                style = Headline2
            )
            Spacer(modifier = Modifier.height(23.dp))
            Text(
                text = "여성",
                style = Body1
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                ChipItem(
                    chipType = ChipType.BORDER,
                    currentSelectedId = setOf(if (age == 20 && gender == UserGender.Female) 1 else 0),
                    chipId = 1,
                    chipText = "20대",
                    onSelectChip = {
                        age = 20
                        gender = UserGender.Female
                    }
                )
                ChipItem(
                    chipType = ChipType.BORDER,
                    currentSelectedId = setOf(if (age == 30 && gender == UserGender.Female) 1 else 0),
                    chipId = 1,
                    chipText = "30대",
                    onSelectChip = {
                        age = 30
                        gender = UserGender.Female
                    }
                )
                ChipItem(
                    chipType = ChipType.BORDER,
                    currentSelectedId = setOf(if (age == 40 && gender == UserGender.Female) 1 else 0),
                    chipId = 1,
                    chipText = "40대",
                    onSelectChip = {
                        age = 40
                        gender = UserGender.Female
                    }
                )
                ChipItem(
                    chipType = ChipType.BORDER,
                    currentSelectedId = setOf(if (age == 50 && gender == UserGender.Female) 1 else 0),
                    chipId = 1,
                    chipText = "50대 이상",
                    onSelectChip = {
                        age = 50
                        gender = UserGender.Female
                    }
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "남성",
                style = Body1
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                ChipItem(
                    chipType = ChipType.BORDER,
                    currentSelectedId = setOf(if (age == 20 && gender == UserGender.Male) 1 else 0),
                    chipId = 1,
                    chipText = "20대",
                    onSelectChip = {
                        age = 20
                        gender = UserGender.Male
                    }
                )
                ChipItem(
                    chipType = ChipType.BORDER,
                    currentSelectedId = setOf(if (age == 30 && gender == UserGender.Male) 1 else 0),
                    chipId = 1,
                    chipText = "30대",
                    onSelectChip = {
                        age = 30
                        gender = UserGender.Male
                    }
                )
                ChipItem(
                    chipType = ChipType.BORDER,
                    currentSelectedId = setOf(if (age == 40 && gender == UserGender.Male) 1 else 0),
                    chipId = 1,
                    chipText = "40대",
                    onSelectChip = {
                        age = 40
                        gender = UserGender.Male
                    }
                )
                ChipItem(
                    chipType = ChipType.BORDER,
                    currentSelectedId = setOf(if (age == 50 && gender == UserGender.Male) 1 else 0),
                    chipId = 1,
                    chipText = "50대 이상",
                    onSelectChip = {
                        age = 50
                        gender = UserGender.Male
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            ConfirmButton(
                modifier = Modifier.fillMaxWidth(),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                onClick = {
                    onResult(age, gender)
                    onDismissRequest()
                }
            ) { style ->
                Text(
                    text = "선택 완료",
                    style = style
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun StatisticsUserGroupScreenPreview() {
    StatisticsUserGroupScreen(
        appState = rememberApplicationState(),
        model = StatisticsUserGroupModel(state = StatisticsUserGroupState.Init),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onResult = { _, _ -> }
    )
}
