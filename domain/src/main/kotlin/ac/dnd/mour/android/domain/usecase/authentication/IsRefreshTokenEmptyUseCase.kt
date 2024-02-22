package ac.dnd.mour.android.domain.usecase.authentication

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.repository.TokenRepository
import javax.inject.Inject

class IsRefreshTokenEmptyUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return if (tokenRepository.refreshToken.isEmpty()) {
            Result.failure(ServerException("Client Error", "Refresh token is empty"))
        } else {
            Result.success(Unit)
        }
    }
}
