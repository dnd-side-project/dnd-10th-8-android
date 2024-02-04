package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewType

sealed interface HistoryIntent {
    data class ClickTab(val type: HistoryViewType) : HistoryIntent
}
