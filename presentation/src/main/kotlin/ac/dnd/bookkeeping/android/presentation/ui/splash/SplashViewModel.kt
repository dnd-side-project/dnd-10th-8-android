package ac.dnd.bookkeeping.android.presentation.ui.splash

import ac.dnd.bookkeeping.android.presentation.common.root.LoginRootConstant
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel() {

    fun navigateToMain(
        navController: NavHostController,
        rootEntryPoint: String
    ) {
        navController.navigate(ScreenRootConstant.MAIN_GRAPH) {
            popUpTo(rootEntryPoint) {
                inclusive = true
            }
        }
    }

    fun navigateToLogin(
        navController: NavHostController,
        rootEntryPoint: String
    ) {
        navController.navigate(LoginRootConstant.LOGIN_MAIN) {
            popUpTo(rootEntryPoint) {
                inclusive = true
            }
        }
    }

}
