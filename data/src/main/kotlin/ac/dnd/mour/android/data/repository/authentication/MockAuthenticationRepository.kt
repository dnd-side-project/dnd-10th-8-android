package ac.dnd.mour.android.data.repository.authentication

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.domain.model.authentication.JwtToken
import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.legacy.Login
import ac.dnd.mour.android.domain.model.legacy.Register
import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockAuthenticationRepository @Inject constructor(
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
        randomShortDelay()
        return Result.success(
            JwtToken(
                accessToken = "mock_access_token",
                refreshToken = "mock_refresh_token"
            )
        )
    }

    override suspend fun login(
        socialId: Long,
        email: String
    ): Result<Login> {
        randomShortDelay()
        return Result.failure(ServerException("MEMBER_001", "사용자 정보가 존재하지 않습니다."))
    }

    override suspend fun logout(): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
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
        randomLongDelay()
        return Result.success(Register(-1L))
    }

    override suspend fun withdraw(): Result<Unit> {
        randomLongDelay()
        return Result.success(Unit)
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }

    companion object {
        private const val REFRESH_TOKEN = "mock_refresh_token"
        private const val ACCESS_TOKEN = "mock_access_token"
    }
}
