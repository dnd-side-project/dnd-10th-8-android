package ac.dnd.mour.android.presentation.ui.main.home.history.detail

sealed interface HistoryDetailState {
    data object Init : HistoryDetailState
    data object Loading : HistoryDetailState
}
