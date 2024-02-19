package ac.dnd.mour.android.presentation.ui.main.home.statistics.me.event

import ac.dnd.mour.android.domain.model.feature.statistics.MyStatistics

data class StatisticsMeEventModel(
    val state: StatisticsMeEventState,
    val myStatistics: MyStatistics,
    val initialEventId: Long,
    val isGive: Boolean
)
