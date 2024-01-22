package ac.dnd.bookkeeping.android.presentation.common.view

import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable

val CustomSnackBarHost: @Composable (SnackbarHostState) -> Unit =
    { snackBarHostState: SnackbarHostState ->
        SnackbarHost(
            hostState = snackBarHostState,
        ) { snackBarData ->
            SnackBarScreen(snackBarData.message)
        }
    }
