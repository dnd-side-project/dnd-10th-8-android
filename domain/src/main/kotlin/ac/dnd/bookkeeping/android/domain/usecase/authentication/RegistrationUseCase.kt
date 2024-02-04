package ac.dnd.bookkeeping.android.domain.usecase.authentication

import ac.dnd.bookkeeping.android.domain.model.legacy.Register
import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        socialId: Long,
        email: String,
        profileImageUrl: String,
        name: String,
        nickname: String,
        gender: String,
        birth: String
    ): Result<Register> {
        return authenticationRepository.register(
            socialId = socialId,
            email = email,
            profileImageUrl = profileImageUrl,
            name = name,
            nickname = nickname,
            gender = gender,
            birth = birth
        )
    }
}
