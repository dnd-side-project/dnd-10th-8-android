package ac.dnd.mour.android.domain.usecase.feature.statistics

import ac.dnd.mour.android.domain.model.feature.statistics.GroupStatistics
import ac.dnd.mour.android.domain.repository.StatisticsRepository
import javax.inject.Inject

class GetGroupStatisticsUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(
        gender: String,
        range: Int
    ): Result<List<GroupStatistics>> {
        return statisticsRepository.getGroupStatistics(
            gender = gender,
            range = range
        )
    }
}
