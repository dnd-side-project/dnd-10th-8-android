package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

sealed interface LoginOnBoardingIntent {
    data object GoToNextStep : LoginOnBoardingIntent
}
