package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main

import ac.dnd.bookkeeping.android.domain.model.legacy.HistoryInfoLegacy
import androidx.compose.runtime.Immutable

@Immutable
data class HistoryMainModel(
    val state: HistoryMainState,
    val historyInfo: HistoryInfoLegacy
)
