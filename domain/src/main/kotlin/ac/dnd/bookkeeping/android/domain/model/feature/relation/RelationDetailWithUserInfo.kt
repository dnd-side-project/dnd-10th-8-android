package ac.dnd.bookkeeping.android.domain.model.feature.relation

data class RelationDetailWithUserInfo(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val memo: String,
    val group: RelationDetailGroup,
    val giveMoney: Long,
    val takeMoney: Long
)
