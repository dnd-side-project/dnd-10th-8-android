package ac.dnd.mour.android.domain.usecase.authentication

import ac.dnd.mour.android.domain.repository.TokenRepository
import javax.inject.Inject

class UpdateJwtTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return tokenRepository.refreshToken(
            refreshToken = tokenRepository.refreshToken
        ).map { }
    }
}
