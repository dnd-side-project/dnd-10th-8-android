package ac.dnd.mour.android.domain.usecase.authentication

import ac.dnd.mour.android.domain.model.legacy.Login
import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import ac.dnd.mour.android.domain.usecase.member.GetProfileUseCase
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
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
                Firebase.analytics.run {
                    setUserId(profile.id.toString())
                    setUserProperty("email", profile.email)
                    setUserProperty("name", profile.name)
                    setUserProperty("nickname", profile.nickname)
                }
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
