package ac.dnd.mour.android.presentation.ui.main.login.onboarding

sealed interface LoginOnBoardingIntent {
    data object Click : LoginOnBoardingIntent
}
