package ac.dnd.bookkeeping.android.data.repository.feature.statistics

import ac.dnd.bookkeeping.android.domain.model.feature.statistics.GroupStatistics
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatisticsItem
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
                give = listOf(
                    MyStatisticsItem(
                        event = "결혼",
                        relationName = "친구",
                        groupName = "veri",
                        money = 4162,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "생일",
                        relationName = "가족",
                        groupName = "antica",
                        money = 9402,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "그외",
                        relationName = "가족",
                        groupName = "anticas",
                        money = 14022,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                ),
                take = listOf(
                    MyStatisticsItem(
                        event = "결혼",
                        relationName = "친구",
                        groupName = "veri",
                        money = 4162,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "생일",
                        relationName = "가족",
                        groupName = "antica",
                        money = 9402,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "그외",
                        relationName = "가족",
                        groupName = "anticas",
                        money = 14022,
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
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
