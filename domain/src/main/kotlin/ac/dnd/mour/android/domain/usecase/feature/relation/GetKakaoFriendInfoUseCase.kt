package ac.dnd.mour.android.domain.usecase.feature.relation

import ac.dnd.mour.android.domain.model.feature.relation.KakaoFriendInfo
import ac.dnd.mour.android.domain.repository.KakaoFriendRepository
import javax.inject.Inject

class GetKakaoFriendInfoUseCase @Inject constructor(
    private val kakaoFriendRepository: KakaoFriendRepository
) {
    suspend operator fun invoke(): Result<KakaoFriendInfo> {
        return kakaoFriendRepository.loadFriendInfo()
    }
}
