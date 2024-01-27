package ac.dnd.bookkeeping.android.data.di

import ac.dnd.bookkeeping.android.data.repository.authentication.MockAuthenticationRepository
import ac.dnd.bookkeeping.android.data.repository.bookkeeping.MockBookkeepingRepository
import ac.dnd.bookkeeping.android.data.repository.sociallogin.KakaoLoginRepositoryImpl
import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import ac.dnd.bookkeeping.android.domain.repository.BookkeepingRepository
import ac.dnd.bookkeeping.android.domain.repository.sociallogin.KakaoLoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsBookkeepingRepository(
        mockBookkeepingRepository: MockBookkeepingRepository
    ): BookkeepingRepository

    @Binds
    @Singleton
    abstract fun bindsAuthenticationRepository(
        authenticationRepository: MockAuthenticationRepository
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindsKakaoLoginRepository(
        kakaoLoginRepositoryImpl: KakaoLoginRepositoryImpl
    ): KakaoLoginRepository
}
