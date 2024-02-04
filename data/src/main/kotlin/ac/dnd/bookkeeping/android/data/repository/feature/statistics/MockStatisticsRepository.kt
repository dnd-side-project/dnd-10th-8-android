package ac.dnd.bookkeeping.android.data.repository.feature.statistics

import ac.dnd.bookkeeping.android.domain.model.feature.statistics.GroupStatistics
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatisticsEvent
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatisticsGive
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatisticsTake
import ac.dnd.bookkeeping.android.domain.repository.StatisticsRepository
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class MockStatisticsRepository @Inject constructor() : StatisticsRepository {

    override suspend fun getMyStatistics(
        year: Int,
        month: Int?
    ): Result<MyStatistics> {
        randomLongDelay()
        return Result.success(
            MyStatistics(
                give = MyStatisticsGive(
                    event = listOf(
                        MyStatisticsEvent(
                            name = "Deirdre Guerra",
                            group = "veri",
                            money = 4162,
                            day = LocalDate(2021, 10, 1)
                        ),
                        MyStatisticsEvent(
                            name = "Merritt Mccarthy",
                            group = "ex",
                            money = 9402,
                            day = LocalDate(2021, 10, 1)
                        ),
                    )
                ),
                take = MyStatisticsTake(
                    event = listOf(
                        MyStatisticsEvent(
                            name = "Calvin Romero",
                            group = "ex",
                            money = 9402,
                            day = LocalDate(2021, 10, 1)
                        ),
                        MyStatisticsEvent(
                            name = "Merritt Mccarthy",
                            group = "ex",
                            money = 9402,
                            day = LocalDate(2021, 10, 1)
                        ),
                    )
                )
            )
        )
    }

    override suspend fun getGroupStatistics(
        gender: String,
        range: Int
    ): Result<GroupStatistics> {
        randomShortDelay()
        return Result.success(
            GroupStatistics(
                marriage = 3123,
                birth = 7383,
                baby = 9370,
                babyBirth = 4026,
                bizopen = 3068,
                etc = 3819
            )
        )
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
