package ac.dnd.mour.android.presentation.ui.main.home.history

sealed interface HistoryState {
    data object Init : HistoryState
    data object Loading : HistoryState
}
