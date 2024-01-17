package ac.dnd.bookkeeping.android.presentation.common.state

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.SystemUiController

@Stable
class ApplicationState(
    val bottomBarState: MutableState<Boolean>,
    val navController: NavHostController,
    val systemUiController: SystemUiController,
    val scaffoldState: ScaffoldState
)