package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.feature.heart.Heart
import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import kotlinx.datetime.LocalDate

interface HeartRepository {

    suspend fun addHeart(
        relationId: Long,
        give: Boolean,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Long>

    suspend fun editHeart(
        id: Long,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Unit>

    suspend fun deleteHeart(
        id: Long,
    ): Result<Unit>

    suspend fun addUnrecordedHeart(
        scheduleId: Long,
        money: Long,
        tags: List<String>
    ): Result<Long>

    suspend fun getHeartList(
        sort: String,
        name: String
    ): Result<List<Heart>>

    suspend fun getRelatedHeartList(
        id: Long,
        sort: String
    ): Result<List<RelatedHeart>>
}
