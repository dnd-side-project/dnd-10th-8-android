package ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting

sealed interface RegistrationCollectingEvent {
    data object OnClickSubmit : RegistrationCollectingEvent
}
