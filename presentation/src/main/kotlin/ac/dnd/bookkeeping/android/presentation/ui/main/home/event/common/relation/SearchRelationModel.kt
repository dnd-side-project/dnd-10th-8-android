package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation

import ac.dnd.bookkeeping.android.domain.model.group.GroupWithRelation
import androidx.compose.runtime.Immutable

@Immutable
data class SearchRelationModel(
    val state: SearchRelationState,
    val groups: List<GroupWithRelation>
)
