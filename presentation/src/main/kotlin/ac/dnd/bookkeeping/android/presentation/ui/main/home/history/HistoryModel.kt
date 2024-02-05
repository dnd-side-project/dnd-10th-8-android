package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.domain.model.feature.group.GroupWithRelationDetail
import ac.dnd.bookkeeping.android.domain.model.legacy.HistoryInfoLegacy
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewType
import androidx.compose.runtime.Immutable

@Immutable
data class HistoryModel(
    val state: HistoryState,
    val info: HistoryInfoLegacy,
    val viewType: HistoryViewType,
    val groups: List<GroupWithRelationDetail>
)
