package ac.dnd.bookkeeping.android.domain.model.feature.group

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationSimple

data class GroupWithRelation(
    val id: Long,
    val name: String,
    val relationList: List<RelationSimple>
)
