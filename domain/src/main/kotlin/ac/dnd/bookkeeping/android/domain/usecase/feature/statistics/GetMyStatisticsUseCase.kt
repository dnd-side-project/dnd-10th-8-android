package ac.dnd.bookkeeping.android.domain.usecase.feature.statistics

import ac.dnd.bookkeeping.android.domain.model.statistics.MyStatistics
import ac.dnd.bookkeeping.android.domain.repository.StatisticsRepository
import javax.inject.Inject

class GetMyStatisticsUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int
    ): Result<MyStatistics> {
        return statisticsRepository.getMyStatistics(
            year = year,
            month = month
        )
    }
}
