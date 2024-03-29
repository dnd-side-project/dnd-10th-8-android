package ac.dnd.mour.android.presentation.ui.main.home.history

import ac.dnd.mour.android.domain.model.feature.group.GroupWithRelationDetail
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedSchedule
import androidx.compose.runtime.Immutable

@Immutable
data class HistoryModel(
    val state: HistoryState,
    val groups: List<GroupWithRelationDetail>,
    val unrecordedSchedule: List<UnrecordedSchedule>
)
