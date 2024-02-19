package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.feature.statistics.GroupStatistics
import ac.dnd.mour.android.domain.model.feature.statistics.MyStatistics

interface StatisticsRepository {

    suspend fun getMyStatistics(
        year: Int,
        month: Int? = null
    ): Result<MyStatistics>

    suspend fun getGroupStatistics(
        gender: String,
        range: Int
    ): Result<List<GroupStatistics>>
}
