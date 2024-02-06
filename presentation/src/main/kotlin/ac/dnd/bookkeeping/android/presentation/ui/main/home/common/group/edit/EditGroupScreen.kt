package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.edit

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun EditGroupScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    prevGroup: Group,
    onResult: (group: Group) -> Unit,
    viewModel: EditGroupViewModel = hiltViewModel()
) {
    val model: EditGroupModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        EditGroupModel(
            state = state,
            group = prevGroup
        )
    }
    ErrorObserver(viewModel)

    EditGroupScreen(
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
private fun EditGroupScreen(
    appState: ApplicationState,
    model: EditGroupModel,
    event: EventFlow<EditGroupEvent>,
    intent: (EditGroupIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: (group: Group) -> Unit,
) {
    var nameText by remember { mutableStateOf("") }

    fun editGroup(event: EditGroupEvent.EditGroup) {
        when (event) {
            is EditGroupEvent.EditGroup.Success -> {
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
                .padding(
                    horizontal = Space20
                )
        ) {
            Spacer(modifier = Modifier.height(Space20))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Space8)
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
                text = "그룹 수정",
                style = Headline2.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = Space8)
            )
            Spacer(modifier = Modifier.height(Space20))
            TypingTextField(
                text = nameText,
                textType = TypingTextFieldType.Basic,
                hintText = "그룹명을 입력해주세요.",
                onValueChange = {
                    nameText = it
                },
                modifier = Modifier.height(48.dp),
                maxTextLength = 8,
                trailingIconContent = {
                    Text(
                        text = "${nameText.length}/8"
                    )
                    if (nameText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                nameText = ""
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = null,
                                modifier = Modifier.size(Space20)
                            )
                        }
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
                isEnabled = nameText.isNotEmpty(),
                onClick = {
                    intent(
                        EditGroupIntent
                            .OnEdit(
                                groupId = model.group.id,
                                name = nameText
                            )
                    )
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
                is EditGroupEvent.EditGroup -> editGroup(event)
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun EditGroupScreenPreview() {
    EditGroupScreen(
        appState = rememberApplicationState(),
        model = EditGroupModel(
            state = EditGroupState.Init,
            group = Group(0, "이전 그룹 네임")
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onResult = {},
    )
}
