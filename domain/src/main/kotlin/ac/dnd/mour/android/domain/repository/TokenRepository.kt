package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.authentication.JwtToken
import kotlinx.coroutines.flow.StateFlow

interface TokenRepository {

    var refreshToken: String

    var accessToken: String

    val isRefreshTokenInvalid: StateFlow<Boolean>

    suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken>
}
