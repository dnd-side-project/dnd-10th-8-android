package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main

sealed interface HistoryMainState {
    data object Init : HistoryMainState
    data object Loading : HistoryMainState
}
