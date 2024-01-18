package ac.dnd.bookkeeping.android.presentation.ui.splash

import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel() {

    fun navigateToMain(
        navController: NavHostController
    ) {
        navController.navigate(ScreenRootConstant.MAIN_GRAPH) {
            popUpTo(ScreenRootConstant.SPLASH) {
                inclusive = true
            }
        }
    }

    fun navigateToLogin(
        navController: NavHostController
    ) {
    }

}
