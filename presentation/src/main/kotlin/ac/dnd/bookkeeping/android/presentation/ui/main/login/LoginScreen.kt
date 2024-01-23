package ac.dnd.bookkeeping.android.presentation.ui.main.login

import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    appState: ApplicationState,
    viewModel: LoginViewModel = hiltViewModel()
) {

    Observer(
        appState = appState,
        viewModel = viewModel
    )

    val nextStageState = remember { mutableStateOf(false) }
    if (nextStageState.value) {
        appState.navController.navigate(LoginConstant.ROUTE_STEP_2)
        nextStageState.value = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
    ) {
        Text(
            text = "Login Screen",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(30.dp)
                .fillMaxWidth()
                .clickable {
                    nextStageState.value = true
                },
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            Text(
                text = "next",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
private fun Observer(
    appState: ApplicationState,
    viewModel: LoginViewModel
) {
    ErrorObserver(viewModel)
}
