package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail

sealed interface HistoryDetailState {
    data object Init : HistoryDetailState
    data object Loading : HistoryDetailState
}
