package ac.dnd.bookkeeping.android.domain.model.feature.relation

data class RelationDetail(
    val id: Long,
    val name: String,
    val group: RelationDetailGroup,
    val giveMoney: Long,
    val takeMoney: Long
)

data class RelationDetailGroup(
    val id: Long,
    val name: String
)
