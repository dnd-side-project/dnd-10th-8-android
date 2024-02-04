package ac.dnd.bookkeeping.android.domain.model.feature.heart

data class Heart(
    val id: Long,
    val relationId: Long,
    val give: Boolean,
    val name: String,
    val group: HeartGroup,
    val giveHistories: List<Long>,
    val takeHistories: List<Long>
)

data class HeartGroup(
    val id: Long,
    val name: String
)

