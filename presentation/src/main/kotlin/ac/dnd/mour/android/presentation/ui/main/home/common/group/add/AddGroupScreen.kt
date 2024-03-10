package ac.dnd.mour.android.presentation.ui.main.home.common.group.add

import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space16
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.theme.Space8
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.logevent.LogEventUtil
import ac.dnd.mour.android.presentation.common.util.logevent.viewLogEvent
import ac.dnd.mour.android.presentation.common.view.BottomSheetScreen
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    onResult: (Group) -> Unit,
    viewModel: AddGroupViewModel = hiltViewModel()
) {
    val model: AddGroupModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AddGroupModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    LaunchedEffect(Unit) {
        viewLogEvent(
            LogEventUtil.VIEW_ADD_NEW_GROUP,
            block = {

            }
        )
    }

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
private fun AddGroupScreen(
    appState: ApplicationState,
    model: AddGroupModel,
    event: EventFlow<AddGroupEvent>,
    intent: (AddGroupIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: (Group) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    fun addGroup(event: AddGroupEvent.AddGroup) {
        when (event) {
            is AddGroupEvent.AddGroup.Success -> {
                onResult(event.group)
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
                .background(Gray000)
                .padding(horizontal = Space20)
        ) {
            Spacer(modifier = Modifier.height(Space20))
            Box(
                modifier = Modifier
                    .padding(Space8)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onDismissRequest()
                        }
                )
            }
            Text(
                text = "새 그룹 추가",
                style = Headline2.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = Space8)
            )
            Spacer(modifier = Modifier.height(Space20))
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${text.length}/8",
                            style = Body1.merge(
                                color = Gray600,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                        if (text.isNotEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(Space20)
                                    .clickable {
                                        text = ""
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.width(Space16))
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))

            ConfirmButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Space12),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                isEnabled = text.isNotEmpty(),
                onClick = {
                    viewLogEvent(
                        LogEventUtil.CLICK_REGISTRATION_ADD_NEW_GROUP,
                        block = {

                        }
                    )
                    intent(AddGroupIntent.OnConfirm(text))
                }
            ) {
                Text(
                    text = "등록",
                    style = Headline3.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
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

@Preview(apiLevel = 33)
@Composable
private fun AddGroupScreenPreview() {
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
