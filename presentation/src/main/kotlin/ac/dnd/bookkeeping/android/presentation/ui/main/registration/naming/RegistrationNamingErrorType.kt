package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming

sealed interface RegistrationNamingErrorType {
    data object Init : RegistrationNamingErrorType
    sealed interface InValid : RegistrationNamingErrorType {
        data object Duplication : InValid
        data object NumberOutOfRange : InValid
    }
}
