package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main

import ac.dnd.bookkeeping.android.domain.model.history.HistoryInfo
import androidx.compose.runtime.Immutable

@Immutable
data class HistoryMainModel(
    val state: HistoryMainState,
    val historyInfo: HistoryInfo
)
