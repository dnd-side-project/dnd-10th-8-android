package ac.dnd.bookkeeping.android.data.di

import ac.dnd.bookkeeping.android.domain.repository.sociallogin.SocialLoginRepository
import ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin.GetUserInfoUseCase
import ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin.KakaoLoginUseCase
import ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin.SocialLoginUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSocialLoginUseCase(socialLoginRepository: SocialLoginRepository) = SocialLoginUseCases(
        kakaoLoginUseCase = KakaoLoginUseCase(socialLoginRepository),
        getUserInfoUseCase = GetUserInfoUseCase(socialLoginRepository)
    )
}
