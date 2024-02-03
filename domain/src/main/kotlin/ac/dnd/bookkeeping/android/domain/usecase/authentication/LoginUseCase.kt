package ac.dnd.bookkeeping.android.domain.usecase.authentication

import ac.dnd.bookkeeping.android.domain.model.legacy.Login
import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        socialId: Long,
        email: String,
    ): Result<Login> {
        return authenticationRepository.login(
            socialId = socialId,
            email = email
        )
    }
}
