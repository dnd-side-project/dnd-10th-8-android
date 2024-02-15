package ac.dnd.bookkeeping.android.presentation.ui.main.home.test

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.CHANNEL_1
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.alarm.showNotification
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.add.AddGroupScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get.GetGroupScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.get.SearchRelationScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

// TODO : Debug 로 이동
@Composable
fun TestScreen(
    appState: ApplicationState,
    viewModel: TestViewModel = hiltViewModel()
) {
    val model: TestModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        TestModel(
            state = state
        )
    }
    ErrorObserver(viewModel)

    TestScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@Composable
private fun TestScreen(
    appState: ApplicationState,
    model: TestModel,
    event: EventFlow<TestEvent>,
    intent: (TestIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var isDialogShowing by remember { mutableStateOf(false) }
    var isBottomSheetShowing by remember { mutableStateOf(false) }
    var isSearchRelationShowing by remember { mutableStateOf(false) }
    var isAddGroupShowing by remember { mutableStateOf(false) }
    var isGetGroupShowing by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isErrorText by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TypingTextField(
            TypingTextFieldType.Basic,
            text = searchText,
            hintText = "이름을 입력하세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            onValueChange = {
                searchText = it
                isErrorText = searchText.length > 5
            },
            isError = isErrorText,
            trailingIconContent = {
                IconButton(
                    onClick = {
                        searchText = ""
                        isErrorText = false
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "bottom icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            errorMessageContent = {
                Text(
                    text = "에러 발생",
                    style = Body1.merge(color = Negative),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        )
        Text(
            text = "Setting",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Text(
            text = "dialogState",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    isDialogShowing = true
                }
        )
        Text(
            text = "bottomSheetState",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    isBottomSheetShowing = true
                }
        )
        Text(
            text = "SearchRelation",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    isSearchRelationShowing = true
                }
        )
        Text(
            text = "AddGroup",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    isAddGroupShowing = true
                }
        )
        Text(
            text = "GetGroup",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    isGetGroupShowing = true
                }
        )
        Text(
            text = "Notification",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    scope.launch {
                        context.showNotification(
                            channelId = CHANNEL_1,
                            title = "title",
                            content = "content"
                        )
                    }
                }
        )
    }

    if (isSearchRelationShowing) {
        SearchRelationScreen(
            onDismissRequest = { isSearchRelationShowing = false },
            appState = appState,
            onResult = {
                Timber.d("onResult: $it")
            },
        )
    }

    if (isAddGroupShowing) {
        AddGroupScreen(
            appState = appState,
            onDismissRequest = { isAddGroupShowing = false },
            onResult = {
                Timber.d("onResult")
            }
        )
    }
    if (isGetGroupShowing){
        GetGroupScreen(
            appState = appState,
            onDismissRequest = {
                isGetGroupShowing = false
            },
            groups = listOf(
                Group(0,"sample")
            ),
            onResult = {
                Timber.d("onResult")
            },
            onGroupChange = {

            }
        )
    }
    if (isBottomSheetShowing) {
        BottomSheetScreen(
            onDismissRequest = { isBottomSheetShowing = false },
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 25.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.setting_bottomsheet_title),
                    color = Color.Black,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(60.dp))

                ConfirmButton(
                    modifier = Modifier.fillMaxWidth(),
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Large,
                        type = ConfirmButtonType.Primary
                    ),
                    onClick = {
                        isBottomSheetShowing = false
                    }
                ) { style ->
                    Text(
                        text = stringResource(R.string.setting_bottomsheet_confirm),
                        style = style
                    )
                }
            }
        }
    }

    if (isDialogShowing) {
        DialogScreen(
            title = stringResource(R.string.setting_dialog_title),
            message = stringResource(R.string.setting_dialog_message),
            onCancel = {},
            onDismissRequest = { isDialogShowing = false }
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
private fun TestScreenPreview() {
    TestScreen(
        appState = rememberApplicationState(),
        model = TestModel(
            state = TestState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
