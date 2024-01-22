package ac.dnd.bookkeeping.android.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.coroutines.CoroutineContext

@Stable
class ApplicationState(
    val navController: NavHostController,
    val systemUiController: SystemUiController,
    val coroutineExceptionHandler: (CoroutineContext, Throwable) -> Unit
)

@Composable
fun rememberApplicationState(
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    coroutineExceptionHandler: (CoroutineContext, Throwable) -> Unit = { _, _ -> }
) = remember(
    navController,
    systemUiController,
    coroutineExceptionHandler
) {
    ApplicationState(
        navController = navController,
        systemUiController = systemUiController,
        coroutineExceptionHandler = coroutineExceptionHandler
    )
}
