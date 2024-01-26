package ac.dnd.bookkeeping.android.presentation.ui.main.home.setting

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.CustomTextField
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreen(
    appState: ApplicationState,
    onShowSnackBar: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    Observer(
        appState = appState,
        viewModel = viewModel
    )

    var isDialogShowing by remember { mutableStateOf(false) }
    var isBottomSheetShowing by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            text = searchText.text,
            onTextChange = {
                searchText = TextFieldValue(it)
            },
            trailingIconContent = {
                if (searchText.text.isNotEmpty()) {
                    IconButton(
                        onClick = { searchText = TextFieldValue() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "bottom icon"
                        )
                    }
                }
            },
            hintTextContent = {
                Text(text = stringResource(R.string.input_user_name), color = Color.LightGray)
            },
            contentInnerPadding = PaddingValues(horizontal = 10.dp),
            contentOuterPadding = PaddingValues(horizontal = 10.dp)
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
                    text = stringResource(R.string.setting_bottomsheet_confirm),
                    isMain = true,
                    onClick = {
                        isBottomSheetShowing = false
                    }
                )
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
