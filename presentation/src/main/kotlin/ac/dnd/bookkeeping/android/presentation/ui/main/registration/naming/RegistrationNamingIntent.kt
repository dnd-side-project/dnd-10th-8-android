package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming

sealed interface RegistrationNamingIntent {
    data object SubmitUserInput: RegistrationNamingIntent
}
