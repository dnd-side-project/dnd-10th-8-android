package ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin

import ac.dnd.bookkeeping.android.domain.repository.sociallogin.SocialLoginRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val kakaoLoginRepository: SocialLoginRepository
) {
    suspend operator fun invoke(): Result<String> = kakaoLoginRepository.login()
}
