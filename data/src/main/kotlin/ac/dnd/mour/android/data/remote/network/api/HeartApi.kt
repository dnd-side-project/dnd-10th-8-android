package ac.dnd.mour.android.data.remote.network.api

import ac.dnd.mour.android.data.remote.network.di.AuthHttpClient
import ac.dnd.mour.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.mour.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.mour.android.data.remote.network.model.heart.AddHeartReq
import ac.dnd.mour.android.data.remote.network.model.heart.AddHeartRes
import ac.dnd.mour.android.data.remote.network.model.heart.AddUnrecordedHeartReq
import ac.dnd.mour.android.data.remote.network.model.heart.AddUnrecordedHeartRes
import ac.dnd.mour.android.data.remote.network.model.heart.EditHeartReq
import ac.dnd.mour.android.data.remote.network.model.heart.GetHeartListRes
import ac.dnd.mour.android.data.remote.network.model.heart.GetRelatedHeartListRes
import ac.dnd.mour.android.data.remote.network.util.convert
import ac.dnd.mour.android.data.remote.network.util.parameterFiltered
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import kotlinx.datetime.LocalDate

class HeartApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun addHeart(
        relationId: Long,
        give: Boolean,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<AddHeartRes> {
        return client.post("$baseUrl/api/v1/hearts") {
            setBody(
                AddHeartReq(
                    relationId = relationId,
                    give = give,
                    money = money,
                    day = day,
                    event = event,
                    memo = memo,
                    tags = tags
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editHeart(
        id: Long,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Unit> {
        return client.patch("$baseUrl/api/v1/hearts/$id") {
            setBody(
                EditHeartReq(
                    money = money,
                    day = day,
                    event = event,
                    memo = memo,
                    tags = tags
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun deleteHeart(
        id: Long,
    ): Result<Unit> {
        return client.delete("$baseUrl/api/v1/hearts/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun addUnrecordedHeart(
        scheduleId: Long,
        money: Long,
        tags: List<String>
    ): Result<AddUnrecordedHeartRes> {
        return client.post("$baseUrl/api/v1/hearts/unrecorded-schedule") {
            setBody(
                AddUnrecordedHeartReq(
                    scheduleId = scheduleId,
                    money = money,
                    tags = tags
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun getHeartList(
        sort: String,
        name: String
    ): Result<GetHeartListRes> {
        return client.get("$baseUrl/api/v1/hearts/me") {
            parameterFiltered("sort", sort) // recent, intimacy
            parameterFiltered("name", name)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getRelatedHeartList(
        id: Long,
        sort: String
    ): Result<GetRelatedHeartListRes> {
        return client.get("$baseUrl/api/v1/hearts/me/$id") {
            parameterFiltered("sort", sort) // recent, old
        }.convert(errorMessageMapper::map)
    }
}
