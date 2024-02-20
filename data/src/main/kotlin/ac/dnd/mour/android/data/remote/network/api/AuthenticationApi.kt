package ac.dnd.mour.android.data.remote.network.api

import ac.dnd.mour.android.data.remote.network.di.AuthHttpClient
import ac.dnd.mour.android.data.remote.network.di.NoAuthHttpClient
import ac.dnd.mour.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.mour.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.mour.android.data.remote.network.model.authentication.LoginReq
import ac.dnd.mour.android.data.remote.network.model.authentication.LoginRes
import ac.dnd.mour.android.data.remote.network.model.authentication.RegisterReq
import ac.dnd.mour.android.data.remote.network.model.authentication.RegisterRes
import ac.dnd.mour.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AuthenticationApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun login(
        socialId: Long,
        email: String
    ): Result<LoginRes> {
        return noAuthClient.post("$baseUrl/api/v1/auth/login") {
            setBody(
                LoginReq(
                    socialId = socialId,
                    email = email,
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun logout(): Result<Unit> {
        return client.post("$baseUrl/api/v1/auth/logout")
            .convert(errorMessageMapper::map)
    }

    suspend fun register(
        socialId: Long,
        email: String,
        profileImageUrl: String,
        name: String,
        nickname: String,
        gender: String,
        birth: String
    ): Result<RegisterRes> {
        return noAuthClient.post("$baseUrl/api/v1/members") {
            setBody(
                RegisterReq(
                    socialId = socialId,
                    email = email,
                    profileImageUrl = profileImageUrl,
                    name = name,
                    nickname = nickname,
                    gender = gender,
                    birth = birth
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun withdraw(): Result<Unit> {
        return client.delete("$baseUrl/api/v1/members/me")
            .convert(errorMessageMapper::map)
    }
}
