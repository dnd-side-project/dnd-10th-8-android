package ac.dnd.bookkeeping.android.data.repository.heart

import ac.dnd.bookkeeping.android.domain.model.heart.Heart
import ac.dnd.bookkeeping.android.domain.model.heart.HeartGroup
import ac.dnd.bookkeeping.android.domain.model.heart.RelatedHeart
import ac.dnd.bookkeeping.android.domain.repository.HeartRepository
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class MockHeartRepository @Inject constructor() : HeartRepository {

    override suspend fun addHeart(
        relationId: Long,
        give: Boolean,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Long> {
        randomShortDelay()
        return Result.success(-1)
    }

    override suspend fun editHeart(
        id: Long,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun deleteHeart(
        id: Long
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun addUnrecordedHeart(
        scheduleId: Long,
        money: Long,
        tags: List<String>
    ): Result<Long> {
        randomShortDelay()
        return Result.success(-1)
    }

    override suspend fun getHeartList(
        sort: String,
        name: String
    ): Result<Heart> {
        randomShortDelay()
        return Result.success(
            Heart(
                id = 2059,
                relationId = 4920,
                give = false,
                name = "Lorie Adams",
                group = HeartGroup(
                    id = 7435,
                    name = "Octavio Hayes"
                ),
                giveHistories = listOf(
                    1_000L,
                    2_000L,
                    3_000L,
                    10_000L,
                    100_000L,
                    1_000_000L
                ),
                takeHistories = listOf(
                    1_000L,
                    2_000L,
                    3_000L,
                    10_000L,
                    100_000L,
                    1_000_000L
                )
            )
        )
    }

    override suspend fun getRelatedHeartList(
        id: Long,
        sort: String
    ): Result<RelatedHeart> {
        randomShortDelay()
        return Result.success(
            RelatedHeart(
                id = 7558,
                give = false,
                money = 7166,
                day = LocalDate(2030, 1, 1),
                event = "mediocritatem",
                memo = "luptatum",
                tags = listOf(
                    "tag1",
                    "tag2",
                    "tag3",
                    "tag4",
                    "tag5"
                )
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
