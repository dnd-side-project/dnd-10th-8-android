package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.domain.model.authentication.JwtToken
import kotlinx.coroutines.flow.StateFlow

interface TokenRepository {

    var refreshToken: String

    var accessToken: String

    val refreshFailEvent: EventFlow<Unit>

    suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken>
}
