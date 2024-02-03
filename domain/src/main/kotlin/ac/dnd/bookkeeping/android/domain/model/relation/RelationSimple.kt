package ac.dnd.bookkeeping.android.domain.model.relation

data class RelationSimple(
    val id: Long,
    val name: String,
    val group: RelationSimpleGroup,
)

data class RelationSimpleGroup(
    val id: Long,
    val name: String
)

