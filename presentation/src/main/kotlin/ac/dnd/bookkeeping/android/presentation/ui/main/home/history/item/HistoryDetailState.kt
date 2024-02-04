package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.item

sealed interface HistoryDetailState {
    data object Init : HistoryDetailState
    data object Loading : HistoryDetailState
}
