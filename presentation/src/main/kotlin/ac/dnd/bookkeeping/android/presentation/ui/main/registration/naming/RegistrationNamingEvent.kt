package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming

sealed interface RegistrationNamingEvent {
    sealed interface OnClickSubmit : RegistrationNamingEvent
}
