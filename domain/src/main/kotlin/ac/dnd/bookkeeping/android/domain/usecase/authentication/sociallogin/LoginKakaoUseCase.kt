package ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin

import ac.dnd.bookkeeping.android.domain.repository.sociallogin.KakaoLoginRepository
import javax.inject.Inject

class LoginKakaoUseCase @Inject constructor(
    private val kakaoLoginRepository: KakaoLoginRepository
) {
    suspend operator fun invoke(): Result<String> = kakaoLoginRepository.login()
}
