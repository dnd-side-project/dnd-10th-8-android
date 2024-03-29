package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.legacy.Login
import ac.dnd.mour.android.domain.model.legacy.Register

interface AuthenticationRepository {
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
