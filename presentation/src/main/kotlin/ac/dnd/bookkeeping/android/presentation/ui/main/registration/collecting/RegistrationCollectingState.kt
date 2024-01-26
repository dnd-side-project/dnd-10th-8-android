package ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting

sealed interface RegistrationCollectingState {
    data object Init : RegistrationCollectingState
}
