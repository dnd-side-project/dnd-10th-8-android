package ac.dnd.mour.android.domain.model.feature.heart

data class Heart(
    val relation: HeartRelation,
    val giveHistories: List<Long>,
    val takeHistories: List<Long>
)

data class HeartRelation(
    val id: Long,
    val name: String,
    val group: HeartRelationGroup
)

data class HeartRelationGroup(
    val id: Long,
    val name: String
)
