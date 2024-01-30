package ac.dnd.bookkeeping.android.data.remote.network.api

import ac.dnd.bookkeeping.android.data.remote.network.di.AuthHttpClient
import ac.dnd.bookkeeping.android.data.remote.network.di.NoAuthHttpClient
import ac.dnd.bookkeeping.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.bookkeeping.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.bookkeeping.android.data.remote.network.model.member.CheckNicknameRes
import ac.dnd.bookkeeping.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class MemberApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun checkNickname(
        nickname: String
    ): Result<CheckNicknameRes> {
        return noAuthClient.get("$baseUrl/api/v1/members/check-nickname") {
            parameter("nickname", nickname)
        }.convert(errorMessageMapper::map)
    }
}
