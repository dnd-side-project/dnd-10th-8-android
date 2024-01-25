package ac.dnd.bookkeeping.android.data.repository.authentication

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.domain.model.authentication.JwtToken
import ac.dnd.bookkeeping.android.domain.model.authentication.Login
import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockAuthenticationRepository @Inject constructor(
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
        randomShortDelay()
        return Result.success(
            JwtToken(
                accessToken = "mock_access_token",
                refreshToken = "mock_refresh_token"
            )
        )
    }

    override suspend fun login(
        socialId: String,
        email: String,
        profileImageUrl: String
    ): Result<Login> {
        randomShortDelay()
        return Result.success(
            Login(isNew = true)
        )
    }

    override suspend fun logout(): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
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
