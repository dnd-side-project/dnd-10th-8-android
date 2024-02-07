package ac.dnd.bookkeeping.android.domain.usecase.feature.relation

import ac.dnd.bookkeeping.android.domain.model.feature.relation.KakaoFriendInfo
import ac.dnd.bookkeeping.android.domain.repository.KakaoFriendRepository
import javax.inject.Inject

class GetKakaoFriendInfoUseCase @Inject constructor(
    private val kakaoFriendRepository: KakaoFriendRepository
) {
    suspend operator fun invoke(): Result<KakaoFriendInfo> {
        return kakaoFriendRepository.loadFriendInfo()
    }
}
