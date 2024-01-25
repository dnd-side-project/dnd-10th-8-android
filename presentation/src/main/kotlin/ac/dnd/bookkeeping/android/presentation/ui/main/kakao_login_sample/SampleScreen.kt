package ac.dnd.bookkeeping.android.presentation.ui.main.kakao_login_sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SampleScreen(
    viewModel: SampleViewModel = hiltViewModel()
) {
    val loginState = remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        if (loginState.value) {
            viewModel.login()
        }
        loginState.value = false
    }
}
