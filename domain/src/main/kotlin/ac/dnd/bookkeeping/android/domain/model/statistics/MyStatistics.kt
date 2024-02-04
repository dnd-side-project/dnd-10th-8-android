package ac.dnd.bookkeeping.android.domain.model.statistics

import kotlinx.datetime.LocalDate

data class MyStatistics(
    val give: MyStatisticsGive,
    val take: MyStatisticsTake
)

data class MyStatisticsGive(
    val event: List<MyStatisticsEvent>
)

data class MyStatisticsTake(
    val event: List<MyStatisticsEvent>
)

data class MyStatisticsEvent(
    val name: String,
    val group: String,
    val money: Long,
    val day: LocalDate
)
