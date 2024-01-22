package ac.dnd.bookkeeping.android.domain.usecase.authentication

import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class IsLoginSucceedUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        // TODO: Implement this
        delay(1000L)
        return Result.success(Unit)
    }
}
