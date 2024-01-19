package ac.dnd.bookkeeping.android.presentation.ui.main.home.bookkeeping

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BookkeepingScreen(
    appState: ApplicationState,
    viewModel: BookkeepingViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bookkeeping",
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}
