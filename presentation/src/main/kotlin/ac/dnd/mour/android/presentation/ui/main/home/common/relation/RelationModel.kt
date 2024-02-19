package ac.dnd.mour.android.presentation.ui.main.home.common.relation

import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.presentation.model.relation.RelationDetailWithUserInfoModel
import androidx.compose.runtime.Immutable

@Immutable
data class RelationModel(
    val state: RelationState,
    val relationDetail: RelationDetailWithUserInfoModel,
    val groups: List<Group>
)
