package ac.dnd.mour.android.domain.model.feature.statistics

data class GroupStatistics(
    val event: String,
    val amount: Long
) {
    companion object {
        val empty = GroupStatistics(
            event = "",
            amount = 0
        )
    }
}
