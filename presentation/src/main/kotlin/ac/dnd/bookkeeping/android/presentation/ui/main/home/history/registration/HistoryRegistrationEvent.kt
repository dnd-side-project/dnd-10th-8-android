package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration

sealed interface HistoryRegistrationEvent {
    sealed interface Submit : HistoryRegistrationEvent {
        data object Success : Submit
    }
}
