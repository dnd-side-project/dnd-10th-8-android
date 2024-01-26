package ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin

import ac.dnd.bookkeeping.android.domain.model.sociallogin.UserModel
import ac.dnd.bookkeeping.android.domain.repository.sociallogin.SocialLoginRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val kakaoLoginRepository: SocialLoginRepository
) {
    suspend operator fun invoke(): Result<UserModel> = kakaoLoginRepository.getUserInfo()
}
