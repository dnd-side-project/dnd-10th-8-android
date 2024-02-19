package ac.dnd.mour.android.presentation.ui.main.login.main

sealed interface LoginMainState {
    data object Init : LoginMainState
    data object Loading : LoginMainState
}
