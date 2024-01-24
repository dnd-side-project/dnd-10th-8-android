package ac.dnd.bookkeeping.android.presentation.ui.main.login.main

sealed interface LoginMainIntent {
    data object Click : LoginMainIntent
}
