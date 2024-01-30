package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.type

sealed interface RegistrationMainNamingErrorType {
    data object Init : RegistrationMainNamingErrorType
    sealed interface InValid : RegistrationMainNamingErrorType {
        data object Duplication : InValid
        data object NumberOutOfRange : InValid
    }
}
