package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

sealed interface RegistrationMainErrorType {
    data object Init : RegistrationMainErrorType
    sealed interface InValid : RegistrationMainErrorType {
        data object Duplication : InValid
        data object NumberOutOfRange : InValid
    }
}
