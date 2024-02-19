package ac.dnd.mour.android.domain.usecase.authentication

import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import javax.inject.Inject

class UpdateJwtTokenUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authenticationRepository.refreshToken(
            refreshToken = authenticationRepository.refreshToken
        ).map { }
    }
}
