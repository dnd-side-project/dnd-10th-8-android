package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.authentication.sociallogin.KakaoUserInformation

interface KakaoLoginRepository {
    suspend fun login(): Result<String>
    suspend fun logout(): Result<Boolean>
    suspend fun getUserInfo(): Result<KakaoUserInformation>
}
