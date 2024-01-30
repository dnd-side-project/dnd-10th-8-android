package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

sealed interface RegistrationMainIntent {
    data object OnCheckUserName : RegistrationMainIntent
    data object OnClickSubmit : RegistrationMainIntent
}
