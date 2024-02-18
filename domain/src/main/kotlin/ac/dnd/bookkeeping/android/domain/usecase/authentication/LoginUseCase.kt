package ac.dnd.bookkeeping.android.domain.usecase.authentication

import ac.dnd.bookkeeping.android.domain.model.legacy.Login
import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import ac.dnd.bookkeeping.android.domain.usecase.member.GetProfileUseCase
import io.sentry.Sentry
import io.sentry.protocol.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val getProfileUseCase: GetProfileUseCase
) {
    suspend operator fun invoke(
        socialId: Long,
        email: String,
    ): Result<Login> {
        return authenticationRepository.login(
            socialId = socialId,
            email = email
        ).onSuccess {
            getProfileUseCase().onSuccess { profile ->
                Sentry.setUser(
                    User().apply {
                        this.id = profile.id.toString()
                        this.email = profile.email
                        this.name = profile.name
                        this.username = profile.nickname
                    }
                )
            }.getOrThrow()
        }
    }
}
