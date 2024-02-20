package ac.dnd.mour.android.data.repository.authentication.token

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.domain.model.authentication.JwtToken
import ac.dnd.mour.android.domain.repository.TokenRepository
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockTokenRepository @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : TokenRepository {

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

    override suspend fun resetRefreshTokenInvalidFlag() {
        _isRefreshTokenInvalid.value = false
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
