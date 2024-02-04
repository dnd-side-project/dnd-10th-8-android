package ac.dnd.bookkeeping.android.presentation.ui.main.home.setting

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.CHANNEL_1
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.showNotification
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.group.AddGroupScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation.SearchRelationScreen
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun SettingScreen(
    appState: ApplicationState,
    onShowSnackBar: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Observer(
        appState = appState,
        viewModel = viewModel
    )

    var isDialogShowing by remember { mutableStateOf(false) }
    var isBottomSheetShowing by remember { mutableStateOf(false) }
    var isSearchRelationShowing by remember { mutableStateOf(false) }
    var isAddGroupShowing by remember { mutableStateOf(false) }
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
            text = "snackBarState",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    onShowSnackBar()
                }
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
    DialogScreen(
        isShowing = isDialogShowing,
        title = stringResource(R.string.setting_dialog_title),
        message = stringResource(R.string.setting_dialog_message),
        onCancel = {},
        onDismissRequest = { isDialogShowing = false }
    )
}

@Composable
private fun Observer(
    appState: ApplicationState,
    viewModel: SettingViewModel
) {
    ErrorObserver(viewModel)
}
