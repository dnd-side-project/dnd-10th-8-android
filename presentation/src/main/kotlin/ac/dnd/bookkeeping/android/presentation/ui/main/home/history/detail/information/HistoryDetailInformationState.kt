package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.information

sealed interface HistoryDetailInformationState {
    data object Init : HistoryDetailInformationState
}
