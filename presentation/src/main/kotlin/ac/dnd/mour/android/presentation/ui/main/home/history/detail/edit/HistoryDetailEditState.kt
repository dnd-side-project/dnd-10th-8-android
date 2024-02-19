package ac.dnd.mour.android.presentation.ui.main.home.history.detail.edit

sealed interface HistoryDetailEditState {
    data object Init : HistoryDetailEditState
    data object Loading : HistoryDetailEditState
}
