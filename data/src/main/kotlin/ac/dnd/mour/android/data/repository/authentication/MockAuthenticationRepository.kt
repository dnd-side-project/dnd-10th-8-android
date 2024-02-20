package ac.dnd.mour.android.data.repository.authentication

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.legacy.Login
import ac.dnd.mour.android.domain.model.legacy.Register
import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import ac.dnd.mour.android.domain.repository.TokenRepository
import javax.inject.Inject
import kotlinx.coroutines.delay

class MockAuthenticationRepository @Inject constructor(
    private val tokenRepository: TokenRepository
) : AuthenticationRepository {

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
}
