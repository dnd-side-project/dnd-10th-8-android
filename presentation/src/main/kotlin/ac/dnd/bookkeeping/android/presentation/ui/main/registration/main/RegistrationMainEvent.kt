package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

sealed interface RegistrationMainEvent {

    sealed interface Check : RegistrationMainEvent {
        data object Valid : Check
        data object Invalid : Check
    }

    sealed interface Submit : RegistrationMainEvent {
        data object Success : Submit
        data object Failure : Submit
        data object Error : Submit
    }
}
