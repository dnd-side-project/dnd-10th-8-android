package ac.dnd.bookkeeping.android.presentation.common.state

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Stable
class ApplicationState(
    val bottomBarState: MutableState<Boolean>,
    val navController: NavHostController,
    val systemUiController: SystemUiController,
    val scaffoldState: ScaffoldState
)

@Composable
fun rememberApplicationState(
    bottomBarState: MutableState<Boolean> = remember { mutableStateOf(false) },
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) = remember(
    bottomBarState,
    navController,
    systemUiController,
    scaffoldState
) {
    ApplicationState(
        bottomBarState = bottomBarState,
        navController = navController,
        systemUiController = systemUiController,
        scaffoldState = scaffoldState
    )
}
