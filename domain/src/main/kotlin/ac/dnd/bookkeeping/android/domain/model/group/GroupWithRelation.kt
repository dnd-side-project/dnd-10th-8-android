package ac.dnd.bookkeeping.android.domain.model.group

import ac.dnd.bookkeeping.android.domain.model.relation.RelationSimple

data class GroupWithRelation(
    val id: Long,
    val name: String,
    val relationList: List<RelationSimple>
)
