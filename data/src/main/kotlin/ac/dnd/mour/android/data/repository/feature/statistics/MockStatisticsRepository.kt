package ac.dnd.mour.android.data.repository.feature.statistics

import ac.dnd.mour.android.domain.model.feature.statistics.GroupStatistics
import ac.dnd.mour.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.mour.android.domain.model.feature.statistics.MyStatisticsItem
import ac.dnd.mour.android.domain.repository.StatisticsRepository
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate

class MockStatisticsRepository @Inject constructor() : StatisticsRepository {

    override suspend fun getMyStatistics(
        year: Int,
        month: Int?
    ): Result<MyStatistics> {
        randomShortDelay()
        return Result.success(
            MyStatistics(
                give = listOf(
                    MyStatisticsItem(
                        event = "결혼",
                        relationName = "이다빈",
                        groupName = "친구",
                        money = Random.nextLong(20_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "생일",
                        relationName = "서지원",
                        groupName = "가족",
                        money = Random.nextLong(20_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "그외",
                        relationName = "김경민",
                        groupName = "가족",
                        money = Random.nextLong(50_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                ),
                take = listOf(
                    MyStatisticsItem(
                        event = "결혼",
                        relationName = "박예리나",
                        groupName = "친구",
                        money = Random.nextLong(20_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "생일",
                        relationName = "김진우",
                        groupName = "가족",
                        money = Random.nextLong(20_000, 150_000),
                        day = LocalDate(2021, 10, 1),
                        memo = "asdf"
                    ),
                    MyStatisticsItem(
                        event = "그외",
                        relationName = "장성혁",
                        groupName = "가족",
                        money = Random.nextLong(50_000, 150_000),
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
    ): Result<List<GroupStatistics>> {
        randomShortDelay()
        return Result.success(
            listOf(
                GroupStatistics(
                    event = "결혼",
                    amount = Random.nextLong(50_000, 200_000)
                ),
                GroupStatistics(
                    event = "생일",
                    amount = Random.nextLong(50_000, 400_000)
                ),
                GroupStatistics(
                    event = "돌잔치",
                    amount = Random.nextLong(0, 50_000)
                ),
                GroupStatistics(
                    event = "출산",
                    amount = Random.nextLong(0, 50_000)
                ),
                GroupStatistics(
                    event = "개업",
                    amount = Random.nextLong(40_000, 300_000)
                ),
                GroupStatistics(
                    event = "랜덤이벤트1",
                    amount = Random.nextLong(0, 500_000)
                ),
                GroupStatistics(
                    event = "랜덤이벤트2",
                    amount = Random.nextLong(50_000, 100_000)
                ),
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
