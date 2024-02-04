package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration

sealed interface HistoryRegistrationState {
    data object Init : HistoryRegistrationState
    data object Loading : HistoryRegistrationState
}
