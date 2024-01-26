package ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin

data class SocialLoginUseCases(
    val kakaoLoginUseCase: KakaoLoginUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase
)
