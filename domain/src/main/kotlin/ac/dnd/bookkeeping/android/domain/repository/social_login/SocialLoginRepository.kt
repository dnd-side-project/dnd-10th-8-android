package ac.dnd.bookkeeping.android.domain.repository.social_login

import ac.dnd.bookkeeping.android.domain.model.social_login.UserModel

interface SocialLoginRepository {
    suspend fun login(): Result<String>
    suspend fun logout(): Result<Boolean>
    suspend fun getUserInfo(): Result<UserModel>
}
