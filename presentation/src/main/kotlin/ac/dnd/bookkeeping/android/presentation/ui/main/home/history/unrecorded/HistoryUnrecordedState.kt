package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.unrecorded

sealed interface HistoryUnrecordedState {
    data object Init : HistoryUnrecordedState
    data object Loading : HistoryUnrecordedState
}
