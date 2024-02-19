package ac.dnd.mour.android.domain.model.feature.group

import ac.dnd.mour.android.domain.model.feature.relation.RelationDetail

data class GroupWithRelationDetail(
    val id: Long,
    val name: String,
    val relationList: List<RelationDetail>
)
