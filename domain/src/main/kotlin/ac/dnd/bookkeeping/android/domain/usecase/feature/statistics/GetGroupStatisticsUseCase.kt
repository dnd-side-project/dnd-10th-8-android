package ac.dnd.bookkeeping.android.domain.usecase.feature.statistics

import ac.dnd.bookkeeping.android.domain.model.statistics.GroupStatistics
import ac.dnd.bookkeeping.android.domain.repository.StatisticsRepository
import javax.inject.Inject

class GetGroupStatisticsUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(
        gender: String,
        range: Int
    ): Result<GroupStatistics> {
        return statisticsRepository.getGroupStatistics(
            gender = gender,
            range = range
        )
    }
}
