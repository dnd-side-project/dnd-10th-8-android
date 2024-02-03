package ac.dnd.bookkeeping.android.data.remote.network.api

import ac.dnd.bookkeeping.android.data.remote.network.di.AuthHttpClient
import ac.dnd.bookkeeping.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.bookkeeping.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.bookkeeping.android.data.remote.network.model.heart.AddHeartReq
import ac.dnd.bookkeeping.android.data.remote.network.model.heart.AddHeartRes
import ac.dnd.bookkeeping.android.data.remote.network.model.heart.AddUnrecordedHeartReq
import ac.dnd.bookkeeping.android.data.remote.network.model.heart.AddUnrecordedHeartRes
import ac.dnd.bookkeeping.android.data.remote.network.model.heart.EditHeartReq
import ac.dnd.bookkeeping.android.data.remote.network.model.heart.GetHeartListRes
import ac.dnd.bookkeeping.android.data.remote.network.model.heart.GetRelatedHeartListRes
import ac.dnd.bookkeeping.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.datetime.LocalDate
import javax.inject.Inject

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
        memo: String = "",
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
        sort: String = "recent",
        name: String = ""
    ): Result<GetHeartListRes> {
        return client.get("$baseUrl/api/v1/hearts/me") {
            parameter("sort", sort) // recent, intimacy
            parameter("name", name)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getRelatedHeartList(
        id: Long,
        sort: String = "recent"
    ): Result<GetRelatedHeartListRes> {
        return client.get("$baseUrl/api/v1/hearts/me/$id") {
            parameter("sort", sort) // recent, old
        }.convert(errorMessageMapper::map)
    }
}
