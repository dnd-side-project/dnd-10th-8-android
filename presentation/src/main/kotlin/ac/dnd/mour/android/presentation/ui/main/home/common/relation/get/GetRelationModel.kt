package ac.dnd.mour.android.presentation.ui.main.home.common.relation.get

import ac.dnd.mour.android.domain.model.feature.group.GroupWithRelationSimple
import androidx.compose.runtime.Immutable

@Immutable
data class GetRelationModel(
    val state: GetRelationState,
    val groups: List<GroupWithRelationSimple>
)
