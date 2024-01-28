package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.authentication.JwtToken
import ac.dnd.bookkeeping.android.domain.model.authentication.Login

interface AuthenticationRepository {

    var refreshToken: String

    var accessToken: String

    suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken>

    suspend fun login(
        socialId: Long,
        email: String
    ): Result<Login>

    suspend fun logout(): Result<Unit>

    suspend fun withdraw(): Result<Unit>
}
