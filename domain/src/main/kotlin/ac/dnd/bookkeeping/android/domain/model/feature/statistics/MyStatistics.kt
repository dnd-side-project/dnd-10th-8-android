package ac.dnd.bookkeeping.android.domain.model.feature.statistics

import kotlinx.datetime.LocalDate

data class MyStatistics(
    val give: List<MyStatisticsItem>,
    val take: List<MyStatisticsItem>
) {
    companion object {
        val empty: MyStatistics = MyStatistics(
            give = listOf(),
            take = listOf()
        )
    }
}

data class MyStatisticsItem(
    val event: String,
    val relationName: String,
    val groupName: String,
    val money: Long,
    val day: LocalDate,
    val memo: String
)
