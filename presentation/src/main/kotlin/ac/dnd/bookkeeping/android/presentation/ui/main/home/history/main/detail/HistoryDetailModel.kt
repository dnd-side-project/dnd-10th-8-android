package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail

import ac.dnd.bookkeeping.android.domain.model.legacy.GroupLegacy
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail.type.HistoryViewType
import androidx.compose.runtime.Immutable

@Immutable
data class HistoryDetailModel(
    val state: HistoryDetailState,
    val viewType: HistoryViewType,
    val historyGroups: List<GroupLegacy>,
)
