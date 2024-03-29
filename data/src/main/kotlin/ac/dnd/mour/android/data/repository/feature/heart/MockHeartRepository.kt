package ac.dnd.mour.android.data.repository.feature.heart

import ac.dnd.mour.android.domain.model.feature.heart.Heart
import ac.dnd.mour.android.domain.model.feature.heart.HeartRelation
import ac.dnd.mour.android.domain.model.feature.heart.HeartRelationGroup
import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.mour.android.domain.repository.HeartRepository
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate

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
    ): Result<List<Heart>> {
        randomShortDelay()
        return Result.success(
            listOf(
                Heart(
                    relation = HeartRelation(
                        id = 2059,
                        name = "Lorie Adams",
                        group = HeartRelationGroup(
                            id = 7435,
                            name = "Octavio Hayes"
                        )
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
                ),
                Heart(
                    relation = HeartRelation(
                        id = 6007,
                        name = "Jody Huffman",
                        group = HeartRelationGroup(
                            id = 1855,
                            name = "Randi Sweet"
                        ),
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
        )
    }

    override suspend fun getRelatedHeartList(
        id: Long,
        sort: String
    ): Result<List<RelatedHeart>> {
        randomShortDelay()
        return Result.success(
            listOf(
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
                ),
                RelatedHeart(
                    id = 4800,
                    give = true,
                    money = 8491,
                    day = LocalDate(2024, 2, 25),
                    event = "tristique",
                    memo = "suavitate",
                    tags = listOf(
                        "tag1",
                        "tag2",
                        "tag3",
                        "tag4",
                        "tag5"
                    )
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
