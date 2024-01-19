package ac.dnd.bookkeeping.android.presentation.ui.login

import ac.dnd.bookkeeping.android.presentation.common.root.LoginRootConstant
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): ViewModel() {

    fun navigateToOnBoard(
        navController: NavHostController
    ){
        navController.navigate(LoginRootConstant.LOGIN_ONBOARD){
            popUpTo(LoginRootConstant.LOGIN_MAIN) {
                inclusive = true
            }
        }
    }

    fun navigateToMain(
        navController: NavHostController,
        prevRoot: String
    ){
        navController.navigate(ScreenRootConstant.MAIN_SPLASH){
            popUpTo(prevRoot){
                inclusive = true
            }
        }
    }
}
