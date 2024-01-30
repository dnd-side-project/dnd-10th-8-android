package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

sealed interface RegistrationMainIntent {
    data class OnCheckUserName(val name: String) : RegistrationMainIntent
    data class OnClickSubmit(
        val nickName: String,
        val gender: String,
        val birth: String
    ) : RegistrationMainIntent
}
