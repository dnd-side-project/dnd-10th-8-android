package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

sealed interface LoginOnBoardingState {
    data object Init : LoginOnBoardingState
}
