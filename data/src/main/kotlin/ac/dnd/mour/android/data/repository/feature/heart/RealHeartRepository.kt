package ac.dnd.mour.android.data.repository.feature.heart

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.data.remote.network.api.HeartApi
import ac.dnd.mour.android.data.remote.network.util.toDomain
import ac.dnd.mour.android.domain.model.feature.heart.Heart
import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.mour.android.domain.repository.HeartRepository
import javax.inject.Inject
import kotlinx.datetime.LocalDate

class RealHeartRepository @Inject constructor(
    private val heartApi: HeartApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : HeartRepository {
    override suspend fun addHeart(
        relationId: Long,
        give: Boolean,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Long> {
        return heartApi.addHeart(
            relationId = relationId,
            give = give,
            money = money,
            day = day,
            event = event,
            memo = memo,
            tags = tags
        ).toDomain()
    }

    override suspend fun editHeart(
        id: Long,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Unit> {
        return heartApi.editHeart(
            id = id,
            money = money,
            day = day,
            event = event,
            memo = memo,
            tags = tags
        )
    }

    override suspend fun deleteHeart(
        id: Long
    ): Result<Unit> {
        return heartApi.deleteHeart(
            id = id
        )
    }

    override suspend fun addUnrecordedHeart(
        scheduleId: Long,
        money: Long,
        tags: List<String>
    ): Result<Long> {
        return heartApi.addUnrecordedHeart(
            scheduleId = scheduleId,
            money = money,
            tags = tags
        ).toDomain()
    }

    override suspend fun getHeartList(
        sort: String,
        name: String
    ): Result<List<Heart>> {
        return heartApi.getHeartList(
            sort = sort,
            name = name
        ).toDomain()
    }

    override suspend fun getRelatedHeartList(
        id: Long,
        sort: String
    ): Result<List<RelatedHeart>> {
        return heartApi.getRelatedHeartList(
            id = id,
            sort = sort
        ).toDomain()
    }
}
