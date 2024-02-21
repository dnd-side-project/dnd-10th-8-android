package ac.dnd.mour.android.data.repository.authentication.token

import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.asEventFlow
import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.data.remote.network.api.TokenApi
import ac.dnd.mour.android.domain.model.authentication.JwtToken
import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.repository.TokenRepository
import javax.inject.Inject

class RealTokenRepository @Inject constructor(
    private val tokenApi: TokenApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : TokenRepository {

    override var refreshToken: String
        set(value) = sharedPreferencesManager.setString(REFRESH_TOKEN, value)
        get() = sharedPreferencesManager.getString(REFRESH_TOKEN, "")

    override var accessToken: String
        set(value) = sharedPreferencesManager.setString(ACCESS_TOKEN, value)
        get() = sharedPreferencesManager.getString(ACCESS_TOKEN, "")

    private val _refreshFailEvent: MutableEventFlow<Unit> = MutableEventFlow()
    override val refreshFailEvent: EventFlow<Unit> = _refreshFailEvent.asEventFlow()

    override suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken> {
        return if (refreshToken.isEmpty()) {
            Result.failure(ServerException("Client Error", "refreshToken is empty."))
        } else {
            tokenApi.getAccessToken(
                refreshToken = refreshToken
            ).onSuccess { token ->
                this.refreshToken = token.refreshToken
                this.accessToken = token.accessToken
            }.onFailure { exception ->
                this.refreshToken = ""
                this.accessToken = ""
                _refreshFailEvent.emit(Unit)
            }.map { token ->
                JwtToken(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken
                )
            }
        }
    }

    companion object {
        private const val REFRESH_TOKEN = "refresh_token"
        private const val ACCESS_TOKEN = "access_token"
    }
}
