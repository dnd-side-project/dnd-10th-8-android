package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewType

sealed interface HistoryDetailIntent {
    data class ClickTab(val type: HistoryViewType) : HistoryDetailIntent
}
