package ac.dnd.bookkeeping.android.domain.model.event

data class Group(
    val id: Long,
    val name: String,
    val relations: List<Relation>
)
