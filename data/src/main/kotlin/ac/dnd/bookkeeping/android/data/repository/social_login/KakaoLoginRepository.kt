package ac.dnd.bookkeeping.android.data.repository.social_login

import ac.dnd.bookkeeping.android.domain.model.social_login.UserModel
import ac.dnd.bookkeeping.android.domain.repository.social_login.SocialLoginRepository
import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoLoginRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : SocialLoginRepository {

    override suspend fun login(): Result<String> = runCatching {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            try {
                UserApiClient.loginWithKakaoTalk()
            } catch (error: Throwable) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    throw error
                } else {
                    UserApiClient.loginWithKakaoAccount()
                }
            }
        } else {
            UserApiClient.loginWithKakaoAccount()
        }
    }

    private suspend fun UserApiClient.Companion.loginWithKakaoTalk(): String =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else {
                    val accessToken = token?.accessToken
                    if (accessToken == null) {
                        continuation.resumeWithException(RuntimeException("Can't Receive Kaokao Access Token"))
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
                        continuation.resumeWithException(RuntimeException("Can't Receive Kaokao Access Token"))
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

    override suspend fun getUserInfo(): Result<UserModel> = runCatching {
        suspendCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (user != null) {
                    try {
                        continuation.resume(
                            UserModel(
                                socialId = user.id!!,
                                email = user.kakaoAccount!!.email!!,
                                name = user.kakaoAccount!!.profile!!.nickname!!
                            )
                        )
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }
}
