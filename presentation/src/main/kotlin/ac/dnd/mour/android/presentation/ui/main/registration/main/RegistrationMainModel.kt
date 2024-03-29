package ac.dnd.mour.android.presentation.ui.main.registration.main

import ac.dnd.mour.android.presentation.ui.main.registration.main.type.RegistrationMainNamingErrorType

data class RegistrationMainModel(
    val state: RegistrationMainState,
    val namingErrorType: RegistrationMainNamingErrorType
)
