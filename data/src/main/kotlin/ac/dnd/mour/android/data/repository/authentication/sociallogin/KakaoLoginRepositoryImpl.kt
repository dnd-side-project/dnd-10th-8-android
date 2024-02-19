package ac.dnd.mour.android.data.repository.authentication.sociallogin

import ac.dnd.mour.android.domain.model.authentication.sociallogin.KakaoUserInformation
import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.repository.KakaoLoginRepository
import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoLoginRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : KakaoLoginRepository {

    override suspend fun login(): Result<String> = runCatching {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalkFlow()
        } else {
            UserApiClient.loginWithKakaoAccount()
        }
    }

    private suspend fun loginWithKakaoTalkFlow() = runCatching {
        UserApiClient.loginWithKakaoTalk()
    }
        .onSuccess {
            Result.success(it)
        }
        .onFailure { error ->
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {

                throw ServerException("Cancelled", "User Skip Kakao Login")
            } else {
                UserApiClient.loginWithKakaoAccount()
            }
        }
        .getOrThrow()

    private suspend fun UserApiClient.Companion.loginWithKakaoTalk(): String =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else {
                    val accessToken = token?.accessToken
                    if (accessToken == null) {
                        continuation.resumeWithException(RuntimeException("Can't Receive Kakao Access Token"))
                    } else {
                        continuation.resume(accessToken)
                    }
                }
            }
        }

    private suspend fun UserApiClient.Companion.loginWithKakaoAccount(): String =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else {
                    val accessToken = token?.accessToken
                    if (accessToken == null) {
                        continuation.resumeWithException(RuntimeException("Can't Receive Kakao Access Token"))
                    } else {
                        continuation.resume(accessToken)
                    }
                }
            }
        }

    override suspend fun logout(): Result<Boolean> = runCatching {
        suspendCoroutine { continuation ->
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else {
                    continuation.resume(true)
                }
            }
        }
    }

    override suspend fun getUserInfo(): Result<KakaoUserInformation> = runCatching {
        suspendCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                val userId = user?.id ?: 0L
                val userEmail = user?.kakaoAccount?.email ?: ""
                val userName = user?.kakaoAccount?.profile?.nickname ?: ""
                val userProfile = user?.kakaoAccount?.profile?.profileImageUrl ?: ""
                if (error != null || userId == 0L || userEmail.isEmpty() || userName.isEmpty() || userProfile.isEmpty()) {
                    continuation.resumeWithException(RuntimeException("Can't Receive User Info"))
                } else {
                    continuation.resume(
                        KakaoUserInformation(
                            socialId = userId,
                            email = userEmail,
                            name = userName,
                            profileImageUrl = userProfile
                        )
                    )
                }
            }
        }
    }
}
