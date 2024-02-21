package ac.dnd.mour.android.domain.usecase.authentication

import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.domain.repository.TokenRepository
import javax.inject.Inject

class GetTokenRefreshFailEventFlowUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): EventFlow<Unit> {
        return tokenRepository.refreshFailEvent
    }
}
