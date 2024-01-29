package ac.dnd.bookkeeping.android.domain.usecase.authentication

import ac.dnd.bookkeeping.android.domain.model.authentication.Login
import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authenticationRepository.logout()
    }
}
