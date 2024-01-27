package ac.dnd.bookkeeping.android.domain.repository.sociallogin

import ac.dnd.bookkeeping.android.domain.model.sociallogin.UserModel

interface KakaoLoginRepository {
    suspend fun login(): Result<String>
    suspend fun logout(): Result<Boolean>
    suspend fun getUserInfo(): Result<UserModel>
}
