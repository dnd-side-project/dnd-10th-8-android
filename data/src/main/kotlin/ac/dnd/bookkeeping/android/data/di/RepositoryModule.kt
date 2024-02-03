package ac.dnd.bookkeeping.android.data.di

import ac.dnd.bookkeeping.android.data.repository.authentication.MockAuthenticationRepository
import ac.dnd.bookkeeping.android.data.repository.file.MockFileRepository
import ac.dnd.bookkeeping.android.data.repository.member.MockMemberRepository
import ac.dnd.bookkeeping.android.data.repository.sociallogin.KakaoLoginRepositoryImpl
import ac.dnd.bookkeeping.android.domain.repository.AuthenticationRepository
import ac.dnd.bookkeeping.android.domain.repository.FileRepository
import ac.dnd.bookkeeping.android.domain.repository.KakaoLoginRepository
import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
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
    abstract fun bindsAuthenticationRepository(
        authenticationRepository: MockAuthenticationRepository
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindsMemberRepository(
        memberRepository: MockMemberRepository
    ): MemberRepository

    @Binds
    @Singleton
    abstract fun bindsFileRepository(
        fileRepository: MockFileRepository
    ): FileRepository

    @Binds
    @Singleton
    abstract fun bindsKakaoLoginRepository(
        kakaoLoginRepositoryImpl: KakaoLoginRepositoryImpl
    ): KakaoLoginRepository
}
