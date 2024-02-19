package ac.dnd.mour.android.data.di

import ac.dnd.mour.android.data.remote.local.gallery.GalleryImageRepositoryImpl
import ac.dnd.mour.android.data.repository.authentication.MockAuthenticationRepository
import ac.dnd.mour.android.data.repository.authentication.sociallogin.KakaoLoginRepositoryImpl
import ac.dnd.mour.android.data.repository.feature.group.MockGroupRepository
import ac.dnd.mour.android.data.repository.feature.heart.MockHeartRepository
import ac.dnd.mour.android.data.repository.feature.relation.KakaoFriendRepositoryImpl
import ac.dnd.mour.android.data.repository.feature.relation.MockRelationRepository
import ac.dnd.mour.android.data.repository.feature.schedule.MockScheduleRepository
import ac.dnd.mour.android.data.repository.feature.statistics.MockStatisticsRepository
import ac.dnd.mour.android.data.repository.file.MockFileRepository
import ac.dnd.mour.android.data.repository.gallery.GalleryRepositoryImpl
import ac.dnd.mour.android.data.repository.member.MockMemberRepository
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
    abstract fun bindsGroupRepository(
        groupRepository: MockGroupRepository
    ): GroupRepository

    @Binds
    @Singleton
    abstract fun bindsHeartRepository(
        heartRepository: MockHeartRepository
    ): HeartRepository

    @Binds
    @Singleton
    abstract fun bindsMemberRepository(
        memberRepository: MockMemberRepository
    ): MemberRepository

    @Binds
    @Singleton
    abstract fun bindsRelationRepository(
        relationRepository: MockRelationRepository
    ): RelationRepository

    @Binds
    @Singleton
    abstract fun bindsScheduleRepository(
        scheduleRepository: MockScheduleRepository
    ): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindsStatisticsRepository(
        statisticsRepository: MockStatisticsRepository
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
        fileRepository: MockFileRepository
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
