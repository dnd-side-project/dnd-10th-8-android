package ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin

import ac.dnd.bookkeeping.android.domain.model.sociallogin.UserModel
import ac.dnd.bookkeeping.android.domain.repository.sociallogin.KakaoLoginRepository
import javax.inject.Inject

class GetKakaoUserInfoUseCase @Inject constructor(
    private val kakaoLoginRepository: KakaoLoginRepository
) {
    suspend operator fun invoke(): Result<UserModel> = kakaoLoginRepository.getUserInfo()
}
