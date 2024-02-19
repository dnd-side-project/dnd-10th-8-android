package ac.dnd.mour.android.domain.model.feature.relation

data class RelationSimple(
    val id: Long,
    val name: String,
    val group: RelationSimpleGroup,
)

data class RelationSimpleGroup(
    val id: Long,
    val name: String
)
