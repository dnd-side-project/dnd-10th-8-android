package ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin

import ac.dnd.bookkeeping.android.domain.model.sociallogin.KakaoUserInformation
import ac.dnd.bookkeeping.android.domain.repository.KakaoLoginRepository
import javax.inject.Inject

class GetKakaoUserInfoUseCase @Inject constructor(
    private val kakaoLoginRepository: KakaoLoginRepository
) {
    suspend operator fun invoke(): Result<KakaoUserInformation> = kakaoLoginRepository.getUserInfo()
}
