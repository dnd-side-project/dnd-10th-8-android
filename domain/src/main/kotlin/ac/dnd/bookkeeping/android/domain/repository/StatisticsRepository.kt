package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.statistics.GroupStatistics
import ac.dnd.bookkeeping.android.domain.model.statistics.MyStatistics

interface StatisticsRepository {

    suspend fun getMyStatistics(
        year: Int,
        month: Int? = null
    ): Result<MyStatistics>

    suspend fun getGroupStatistics(
        gender: String,
        range: Int
    ): Result<GroupStatistics>
}
