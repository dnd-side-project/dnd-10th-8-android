package ac.dnd.bookkeeping.android.domain.model.feature.group

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetail

data class GroupWithRelationDetail(
    val id: Long,
    val name: String,
    val relationList: List<RelationDetail>
)
