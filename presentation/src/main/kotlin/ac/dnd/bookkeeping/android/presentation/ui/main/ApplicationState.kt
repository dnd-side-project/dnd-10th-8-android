package ac.dnd.bookkeeping.android.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Stable
class ApplicationState(
    val navController: NavHostController,
    val systemUiController: SystemUiController
)

@Composable
fun rememberApplicationState(
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController()
) = remember(
    navController,
    systemUiController
) {
    ApplicationState(
        navController = navController,
        systemUiController = systemUiController
    )
}
