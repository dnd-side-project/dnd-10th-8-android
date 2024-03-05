package ac.dnd.mour.android.data.remote.network.api

import ac.dnd.mour.android.data.remote.network.di.NoAuthHttpClient
import ac.dnd.mour.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.mour.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.mour.android.data.remote.network.model.authentication.GetAccessTokenRes
import ac.dnd.mour.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import javax.inject.Inject

class TokenApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getAccessToken(
        refreshToken: String
    ): Result<GetAccessTokenRes> {
        return noAuthClient.post("$baseUrl/api/v1/token/reissue") {
            header("Token-Refresh", "Bearer $refreshToken")
        }.convert(errorMessageMapper::map)
    }
}
