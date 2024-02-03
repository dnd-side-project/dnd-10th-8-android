package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.authentication.JwtToken
import ac.dnd.bookkeeping.android.domain.model.legacy.Login
import ac.dnd.bookkeeping.android.domain.model.legacy.Register

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

    suspend fun register(
        socialId: Long,
        email: String,
        profileImageUrl: String,
        name: String,
        nickname: String,
        gender: String,
        birth: String
    ): Result<Register>

    suspend fun withdraw(): Result<Unit>
}
