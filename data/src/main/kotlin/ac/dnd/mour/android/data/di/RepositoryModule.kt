package ac.dnd.mour.android.data.di

import ac.dnd.mour.android.data.remote.local.gallery.GalleryImageRepositoryImpl
import ac.dnd.mour.android.data.repository.authentication.RealAuthenticationRepository
import ac.dnd.mour.android.data.repository.authentication.sociallogin.KakaoLoginRepositoryImpl
import ac.dnd.mour.android.data.repository.authentication.token.RealTokenRepository
import ac.dnd.mour.android.data.repository.feature.group.RealGroupRepository
import ac.dnd.mour.android.data.repository.feature.heart.RealHeartRepository
import ac.dnd.mour.android.data.repository.feature.relation.KakaoFriendRepositoryImpl
import ac.dnd.mour.android.data.repository.feature.relation.RealRelationRepository
import ac.dnd.mour.android.data.repository.feature.schedule.RealScheduleRepository
import ac.dnd.mour.android.data.repository.feature.statistics.RealStatisticsRepository
import ac.dnd.mour.android.data.repository.file.RealFileRepository
import ac.dnd.mour.android.data.repository.gallery.GalleryRepositoryImpl
import ac.dnd.mour.android.data.repository.member.RealMemberRepository
import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import ac.dnd.mour.android.domain.repository.FileRepository
import ac.dnd.mour.android.domain.repository.GalleryImageRepository
import ac.dnd.mour.android.domain.repository.GalleryRepository
import ac.dnd.mour.android.domain.repository.GroupRepository
import ac.dnd.mour.android.domain.repository.HeartRepository
import ac.dnd.mour.android.domain.repository.KakaoFriendRepository
import ac.dnd.mour.android.domain.repository.KakaoLoginRepository
import ac.dnd.mour.android.domain.repository.MemberRepository
import ac.dnd.mour.android.domain.repository.RelationRepository
import ac.dnd.mour.android.domain.repository.ScheduleRepository
import ac.dnd.mour.android.domain.repository.StatisticsRepository
import ac.dnd.mour.android.domain.repository.TokenRepository
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
    abstract fun bindsTokenRepository(
        tokenRepository: RealTokenRepository
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindsAuthenticationRepository(
        authenticationRepository: RealAuthenticationRepository
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindsGroupRepository(
        groupRepository: RealGroupRepository
    ): GroupRepository

    @Binds
    @Singleton
    abstract fun bindsHeartRepository(
        heartRepository: RealHeartRepository
    ): HeartRepository

    @Binds
    @Singleton
    abstract fun bindsMemberRepository(
        memberRepository: RealMemberRepository
    ): MemberRepository

    @Binds
    @Singleton
    abstract fun bindsRelationRepository(
        relationRepository: RealRelationRepository
    ): RelationRepository

    @Binds
    @Singleton
    abstract fun bindsScheduleRepository(
        scheduleRepository: RealScheduleRepository
    ): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindsStatisticsRepository(
        statisticsRepository: RealStatisticsRepository
    ): StatisticsRepository

    @Binds
    @Singleton
    abstract fun bindsKakaoLoginRepository(
        kakaoLoginRepositoryImpl: KakaoLoginRepositoryImpl
    ): KakaoLoginRepository

    @Binds
    @Singleton
    abstract fun bindsKakaoFriendRepository(
        kakaoFriendRepositoryImpl: KakaoFriendRepositoryImpl
    ): KakaoFriendRepository

    @Binds
    @Singleton
    abstract fun bindsFileRepository(
        fileRepository: RealFileRepository
    ): FileRepository

    @Binds
    @Singleton
    abstract fun bindGalleryImageRepository(
        galleryImageRepositoryImpl: GalleryImageRepositoryImpl
    ): GalleryImageRepository

    @Binds
    @Singleton
    abstract fun bindGalleryRepository(
        galleryRepositoryImpl: GalleryRepositoryImpl
    ): GalleryRepository
}
