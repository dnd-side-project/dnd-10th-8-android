package ac.dnd.mour.android.domain.usecase.feature.statistics

import ac.dnd.mour.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.mour.android.domain.repository.StatisticsRepository
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
