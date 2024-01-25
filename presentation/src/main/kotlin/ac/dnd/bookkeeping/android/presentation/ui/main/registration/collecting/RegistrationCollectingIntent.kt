package ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting

sealed interface RegistrationCollectingIntent {
    data object SubmitUserInput : RegistrationCollectingIntent
}
