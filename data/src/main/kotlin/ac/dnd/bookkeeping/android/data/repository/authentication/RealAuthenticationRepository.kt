package ac.dnd.bookkeeping.android.data.repository.authentication

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.data.remote.network.api.AuthenticationApi
import ac.dnd.bookkeeping.android.domain.model.authentication.JwtToken
import ac.dnd.bookkeeping.android.domain.model.authentication.Login
import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import javax.inject.Inject

class RealAuthenticationRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : AuthenticationRepository {

    override var refreshToken: String
        set(value) = sharedPreferencesManager.setString(REFRESH_TOKEN, value)
        get() = sharedPreferencesManager.getString(REFRESH_TOKEN, "")

    override var accessToken: String
        set(value) = sharedPreferencesManager.setString(ACCESS_TOKEN, value)
        get() = sharedPreferencesManager.getString(ACCESS_TOKEN, "")

    override suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken> {
        return authenticationApi.getAccessToken(
            refreshToken = refreshToken
        ).onSuccess { token ->
            this.refreshToken = token.refreshToken
            this.accessToken = token.accessToken
        }.map { token ->
            JwtToken(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken
            )
        }
    }

    override suspend fun login(
        socialId: String,
        email: String,
    ): Result<Login> {
        return authenticationApi.login(
            socialId = socialId,
            email = email,
        ).onSuccess { token ->
            this.refreshToken = token.refreshToken
            this.accessToken = token.accessToken
        }.map { login ->
            Login(isNew = login.isNew)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return authenticationApi.logout()
            .onSuccess {
                this.refreshToken = ""
                this.accessToken = ""
            }
    }

    override suspend fun withdraw(): Result<Unit> {
        return authenticationApi.withdraw()
            .onSuccess {
                this.refreshToken = ""
                this.accessToken = ""
            }
    }

    companion object {
        private const val REFRESH_TOKEN = "refresh_token"
        private const val ACCESS_TOKEN = "access_token"
    }
}
