package ac.dnd.bookkeeping.android.domain.model.event

data class Relation(
    val id: Long,
    val name: String,
    val group: RelationGroup,
    val giveMoney: Long,
    val takeMoney: Long
)

data class RelationGroup(
    val id: Long,
    val name: String
)
