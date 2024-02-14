package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user

import ac.dnd.bookkeeping.android.domain.model.feature.statistics.GroupStatistics

data class StatisticsUserModel(
    val state: StatisticsUserState,
    val groupStatistics: List<GroupStatistics>
)
