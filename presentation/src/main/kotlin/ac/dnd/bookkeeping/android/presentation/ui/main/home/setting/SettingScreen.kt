package ac.dnd.bookkeeping.android.presentation.ui.main.home.setting

import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreen(
    appState: ApplicationState,
    onShowSnackBar: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {

    val dialogIsShowingState = remember { mutableStateOf(false) }
    val bottomSheetIsShowingState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    dialogIsShowingState.value = true
                }
        )
        Text(
            text = "bottomSheetState",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    bottomSheetIsShowingState.value = true
                }
        )
    }
    BottomSheetScreen(bottomSheetIsShowingState = bottomSheetIsShowingState)
    DialogScreen(dialogIsShowingState = dialogIsShowingState)
}
