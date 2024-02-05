package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.get

import ac.dnd.bookkeeping.android.domain.model.feature.group.GroupWithRelationSimple
import androidx.compose.runtime.Immutable

@Immutable
data class GetRelationModel(
    val state: GetRelationState,
    val groups: List<GroupWithRelationSimple>
)
