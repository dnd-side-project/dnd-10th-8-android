package ac.dnd.bookkeeping.android.data.repository.feature.statistics

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.data.remote.network.api.StatisticsApi
import ac.dnd.bookkeeping.android.data.remote.network.util.toDomain
import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.GroupStatistics
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.bookkeeping.android.domain.repository.GroupRepository
import ac.dnd.bookkeeping.android.domain.repository.StatisticsRepository
import javax.inject.Inject

class RealStatisticsRepository @Inject constructor(
    private val statisticsApi: StatisticsApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : StatisticsRepository {
    override suspend fun getMyStatistics(
        year: Int,
        month: Int?
    ): Result<MyStatistics> {
        return statisticsApi.getMyStatistics(
            year = year,
            month = month
        ).toDomain()
    }

    override suspend fun getGroupStatistics(
        gender: String,
        range: Int
    ): Result<List<GroupStatistics>> {
        return statisticsApi.getGroupStatistics(
            gender = gender,
            range = range
        ).toDomain()
    }
}
