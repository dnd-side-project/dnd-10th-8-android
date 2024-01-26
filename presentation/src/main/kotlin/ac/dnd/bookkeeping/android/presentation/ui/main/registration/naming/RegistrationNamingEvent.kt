package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming

sealed interface RegistrationNamingEvent {
    data object GoToNextStep : RegistrationNamingEvent
}
