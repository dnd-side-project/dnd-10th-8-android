package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.domain.model.legacy.HistoryInfoLegacy
import androidx.compose.runtime.Immutable

@Immutable
data class HistoryModel(
    val state: HistoryState,
    val historyInfo: HistoryInfoLegacy
)
