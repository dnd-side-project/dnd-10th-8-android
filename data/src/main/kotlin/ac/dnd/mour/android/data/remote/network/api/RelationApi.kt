package ac.dnd.mour.android.data.remote.network.api

import ac.dnd.mour.android.data.remote.network.di.AuthHttpClient
import ac.dnd.mour.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.mour.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.mour.android.data.remote.network.model.relation.AddRelationReq
import ac.dnd.mour.android.data.remote.network.model.relation.AddRelationRes
import ac.dnd.mour.android.data.remote.network.model.relation.EditRelationReq
import ac.dnd.mour.android.data.remote.network.model.relation.GetRelationListRes
import ac.dnd.mour.android.data.remote.network.model.relation.GetRelationRes
import ac.dnd.mour.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class RelationApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun addRelation(
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<AddRelationRes> {
        return client.post("$baseUrl/api/v1/relations") {
            setBody(
                AddRelationReq(
                    groupId = groupId,
                    name = name,
                    imageUrl = imageUrl,
                    memo = memo
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editRelation(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Unit> {
        return client.patch("$baseUrl/api/v1/relations/$id") {
            setBody(
                EditRelationReq(
                    groupId = groupId,
                    name = name,
                    imageUrl = imageUrl,
                    memo = memo
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun deleteRelation(
        id: Long,
    ): Result<Unit> {
        return client.delete("$baseUrl/api/v1/relations/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun getRelation(
        id: Long
    ): Result<GetRelationRes> {
        return client.get("$baseUrl/api/v1/relations/me/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun getRelationList(
        name: String
    ): Result<GetRelationListRes> {
        return client.get("$baseUrl/api/v1/relations/me") {
            parameter("name", name)
        }.convert(errorMessageMapper::map)
    }
}
