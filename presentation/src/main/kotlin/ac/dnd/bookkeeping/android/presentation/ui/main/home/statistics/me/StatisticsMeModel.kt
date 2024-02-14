
package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me

import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatistics

data class StatisticsMeModel(
    val state: StatisticsMeState,
    val myStatistics: MyStatistics
)
