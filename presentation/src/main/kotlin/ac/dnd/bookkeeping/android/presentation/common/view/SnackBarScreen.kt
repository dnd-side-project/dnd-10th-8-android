package ac.dnd.bookkeeping.android.presentation.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SnackBarScreen(
    message: String,
    dismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        scope.launch {
            snackBarState.showSnackbar(
                message = message,
                actionLabel = "Action",
                duration = SnackbarDuration.Short
            )
            delay(1000L)
            dismiss()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackBarState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { snackBarData ->
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
            ) {
                Text(
                    text = snackBarData.message,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(20.dp),
                )
            }
        }
    }
}
