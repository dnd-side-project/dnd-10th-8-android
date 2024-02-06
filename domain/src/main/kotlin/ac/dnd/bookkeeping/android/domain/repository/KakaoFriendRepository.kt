package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.feature.relation.KakaoFriendInfo

interface KakaoFriendRepository {
    suspend fun loadFriendInfo(): Result<KakaoFriendInfo>
}
