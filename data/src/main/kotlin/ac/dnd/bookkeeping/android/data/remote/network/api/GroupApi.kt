package ac.dnd.bookkeeping.android.data.remote.network.api

import ac.dnd.bookkeeping.android.data.remote.network.di.AuthHttpClient
import ac.dnd.bookkeeping.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.bookkeeping.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.bookkeeping.android.data.remote.network.model.group.AddGroupReq
import ac.dnd.bookkeeping.android.data.remote.network.model.group.AddGroupRes
import ac.dnd.bookkeeping.android.data.remote.network.model.group.EditGroupReq
import ac.dnd.bookkeeping.android.data.remote.network.model.group.GetGroupListRes
import ac.dnd.bookkeeping.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class GroupApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun addGroup(
        name: String
    ): Result<AddGroupRes> {
        return client.post("$baseUrl/api/v1/groups") {
            setBody(
                AddGroupReq(
                    name = name
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editGroup(
        id: Long,
        name: String
    ): Result<Unit> {
        return client.patch("$baseUrl/api/v1/groups/$id") {
            setBody(
                EditGroupReq(
                    name = name
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun deleteGroup(
        id: Long,
    ): Result<Unit> {
        return client.delete("$baseUrl/api/v1/groups/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun getGroupList(): Result<GetGroupListRes> {
        return client.get("$baseUrl/api/v1/groups/me")
            .convert(errorMessageMapper::map)
    }
}
