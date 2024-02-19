package ac.dnd.mour.android.presentation.ui.main.registration.main

import ac.dnd.mour.android.domain.model.error.ServerException

sealed interface RegistrationMainEvent {

    sealed interface Check : RegistrationMainEvent {
        data object Valid : Check
        data object Invalid : Check
    }

    sealed interface Submit : RegistrationMainEvent {
        data object Success : Submit
        data class Failure(val exception: ServerException) : Submit
        data class Error(val exception: Throwable) : Submit
    }
}
