package ac.dnd.mour.android.domain.model.legacy

data class GroupLegacy(
    val id: Long,
    val name: String,
    val relations: List<RelationLegacy>
)
