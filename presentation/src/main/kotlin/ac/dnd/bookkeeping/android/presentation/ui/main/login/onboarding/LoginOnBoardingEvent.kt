package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

sealed interface LoginOnBoardingEvent {
    data object GoToNextStep : LoginOnBoardingEvent
}
