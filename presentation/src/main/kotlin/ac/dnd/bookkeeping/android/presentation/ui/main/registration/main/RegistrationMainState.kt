package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

sealed interface RegistrationMainState {
    data object Init : RegistrationMainState
    data object Loading : RegistrationMainState
}
