package ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting

sealed interface RegistrationCollectingEvent {
    data object GoToNextStep : RegistrationCollectingEvent
}
