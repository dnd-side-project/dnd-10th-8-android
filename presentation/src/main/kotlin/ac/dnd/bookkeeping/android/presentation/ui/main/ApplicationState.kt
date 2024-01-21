package ac.dnd.bookkeeping.android.presentation.ui.main

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
    val navController: NavHostController,
    val systemUiController: SystemUiController,
    val snackBarMessage: MutableState<String>
){
    fun getSnackBarMessage() = snackBarMessage.value

    fun resetSnackBarMessage(){
        snackBarMessage.value = ""
    }

    fun setSnackBarMessage(message: String){
        snackBarMessage.value = message
    }
}

@Composable
fun rememberApplicationState(
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    snackBarMessage: MutableState<String> = remember{ mutableStateOf("") }
) = remember(
    navController,
    systemUiController
) {
    ApplicationState(
        navController = navController,
        systemUiController = systemUiController,
        snackBarMessage = snackBarMessage
    )
}
