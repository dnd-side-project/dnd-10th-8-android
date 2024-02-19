
package ac.dnd.mour.android.presentation.ui.main.home.statistics.me

import ac.dnd.mour.android.domain.model.feature.statistics.MyStatistics

data class StatisticsMeModel(
    val state: StatisticsMeState,
    val myStatistics: MyStatistics
)
