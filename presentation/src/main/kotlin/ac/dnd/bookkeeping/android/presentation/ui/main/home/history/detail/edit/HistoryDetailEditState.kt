package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.edit

sealed interface HistoryDetailEditState {
    data object Init : HistoryDetailEditState
}
