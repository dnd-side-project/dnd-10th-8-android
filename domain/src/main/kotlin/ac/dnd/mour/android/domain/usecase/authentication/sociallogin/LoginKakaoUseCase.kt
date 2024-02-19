package ac.dnd.mour.android.domain.usecase.authentication.sociallogin

import ac.dnd.mour.android.domain.repository.KakaoLoginRepository
import javax.inject.Inject

class LoginKakaoUseCase @Inject constructor(
    private val kakaoLoginRepository: KakaoLoginRepository
) {
    suspend operator fun invoke(): Result<String> = kakaoLoginRepository.login()
}
