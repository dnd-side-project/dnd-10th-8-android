package ac.dnd.mour.android.presentation.ui.main.home.statistics.user

import ac.dnd.mour.android.domain.model.feature.statistics.GroupStatistics

data class StatisticsUserModel(
    val state: StatisticsUserState,
    val groupStatistics: List<GroupStatistics>
)
