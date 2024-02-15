package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.event

import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatistics

data class StatisticsMeEventModel(
    val state: StatisticsMeEventState,
    val myStatistics: MyStatistics,
    val initialEventId: Long,
    val isGive: Boolean
)
