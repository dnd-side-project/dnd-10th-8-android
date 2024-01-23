package ac.dnd.bookkeeping.android.data.remote.network.api

import ac.dnd.bookkeeping.android.data.remote.network.di.NoAuthHttpClient
import ac.dnd.bookkeeping.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.bookkeeping.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.bookkeeping.android.data.remote.network.model.authentication.GetAccessTokenReq
import ac.dnd.bookkeeping.android.data.remote.network.model.authentication.GetAccessTokenRes
import ac.dnd.bookkeeping.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AuthenticationApi @Inject constructor(
    @NoAuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getAccessToken(
        refreshToken: String
    ): Result<GetAccessTokenRes> {
        return client.post("$baseUrl/member/v1/refresh") {
            setBody(
                GetAccessTokenReq(
                    refreshToken = refreshToken
                )
            )
        }.convert(errorMessageMapper::map)
    }
}
