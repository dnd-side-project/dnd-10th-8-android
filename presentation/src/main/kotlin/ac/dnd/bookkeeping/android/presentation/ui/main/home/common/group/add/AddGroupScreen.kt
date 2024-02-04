package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.add

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun AddGroupScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onResult: () -> Unit,
    viewModel: AddGroupViewModel = hiltViewModel()
) {
    val model: AddGroupModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AddGroupModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    AddGroupScreen(
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
@OptIn(ExperimentalMaterialApi::class)
private fun AddGroupScreen(
    appState: ApplicationState,
    model: AddGroupModel,
    event: EventFlow<AddGroupEvent>,
    intent: (AddGroupIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: () -> Unit,
) {
    var text by remember { mutableStateOf("") }

    fun addGroup(event: AddGroupEvent.AddGroup) {
        when (event) {
            AddGroupEvent.AddGroup.Success -> {
                onResult()
                onDismissRequest()
            }
        }
    }

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
                .background(Gray200)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "새 그룹 추가",
                style = Headline2
            )
            Spacer(modifier = Modifier.height(28.dp))
            TypingTextField(
                text = text,
                textType = TypingTextFieldType.Basic,
                hintText = "새 그룹명을 입력해주세요.",
                onValueChange = {
                    text = it
                },
                modifier = Modifier.height(48.dp),
                maxTextLength = 8,
                trailingIconContent = {
                    if (text.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                text = ""
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            ConfirmButton(
                modifier = Modifier.fillMaxWidth(),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                isEnabled = text.isNotEmpty(),
                onClick = {
                    intent(AddGroupIntent.OnConfirm(text))
                }
            ) { style ->
                Text(
                    text = "등록",
                    style = style
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is AddGroupEvent.AddGroup -> addGroup(event)
            }
        }
    }
}

// TODO : Preview 안보임.
@Preview
@Composable
fun AddGroupScreenPreview() {
    AddGroupScreen(
        appState = rememberApplicationState(),
        model = AddGroupModel(state = AddGroupState.Init),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onResult = {}
    )
}
