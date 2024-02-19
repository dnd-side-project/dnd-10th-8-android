package ac.dnd.mour.android.domain.usecase.authentication

import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authenticationRepository.withdraw()
    }
}
