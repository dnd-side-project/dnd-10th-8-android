package ac.dnd.mour.android.data.repository.authentication

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.data.remote.network.api.AuthenticationApi
import ac.dnd.mour.android.domain.model.authentication.JwtToken
import ac.dnd.mour.android.domain.model.legacy.Login
import ac.dnd.mour.android.domain.model.legacy.Register
import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    private val _isRefreshTokenInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isRefreshTokenInvalid: StateFlow<Boolean> = _isRefreshTokenInvalid.asStateFlow()

    override suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken> {
        return authenticationApi.getAccessToken(
            refreshToken = refreshToken
        ).onSuccess { token ->
            this.refreshToken = token.refreshToken
            this.accessToken = token.accessToken
            _isRefreshTokenInvalid.value = false
        }.onFailure { exception ->
            _isRefreshTokenInvalid.value = true
        }.map { token ->
            JwtToken(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken
            )
        }
    }

    override suspend fun login(
        socialId: Long,
        email: String,
    ): Result<Login> {
        return authenticationApi.login(
            socialId = socialId,
            email = email,
        ).onSuccess { token ->
            this.refreshToken = token.refreshToken
            this.accessToken = token.accessToken
        }.map { login ->
            Login(id = login.id)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return authenticationApi.logout()
            .onSuccess {
                this.refreshToken = ""
                this.accessToken = ""
            }
    }

    override suspend fun register(
        socialId: Long,
        email: String,
        profileImageUrl: String,
        name: String,
        nickname: String,
        gender: String,
        birth: String
    ): Result<Register> {
        return authenticationApi.register(
            socialId = socialId,
            email = email,
            profileImageUrl = profileImageUrl,
            name = name,
            nickname = nickname,
            gender = gender,
            birth = birth
        ).onSuccess { register ->
            this.refreshToken = register.refreshToken
            this.accessToken = register.accessToken
        }.map { register ->
            Register(id = register.id)
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
