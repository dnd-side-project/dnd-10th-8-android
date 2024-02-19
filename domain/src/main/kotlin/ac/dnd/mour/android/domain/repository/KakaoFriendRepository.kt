package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.feature.relation.KakaoFriendInfo

interface KakaoFriendRepository {
    suspend fun loadFriendInfo(): Result<KakaoFriendInfo>
}
