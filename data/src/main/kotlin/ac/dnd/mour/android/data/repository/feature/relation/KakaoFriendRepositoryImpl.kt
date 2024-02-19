package ac.dnd.mour.android.data.repository.feature.relation

import ac.dnd.mour.android.domain.model.feature.relation.KakaoFriendInfo
import ac.dnd.mour.android.domain.repository.KakaoFriendRepository
import android.content.Context
import com.kakao.sdk.friend.client.PickerClient
import com.kakao.sdk.friend.model.OpenPickerFriendRequestParams
import com.kakao.sdk.friend.model.PickerOrientation
import com.kakao.sdk.friend.model.ViewAppearance
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoFriendRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : KakaoFriendRepository {

    override suspend fun loadFriendInfo(): Result<KakaoFriendInfo> {
        return runCatching {
            PickerClient.findKakaoFriend()
        }
            .onSuccess {
                Result.success(it)
            }
            .onFailure {
                Result.success(
                    KakaoFriendInfo(
                        name = "",
                        profileImageUrl = ""
                    )
                )
            }
    }


    private suspend fun PickerClient.Companion.findKakaoFriend(): KakaoFriendInfo {
        return suspendCoroutine { continuation ->
            val openPickerFriendRequestParams = OpenPickerFriendRequestParams(
                title = "팝업 싱글 친구 피커",
                viewAppearance = ViewAppearance.AUTO,
                orientation = PickerOrientation.AUTO,
                enableSearch = true,
                enableIndex = true,
                showMyProfile = true,
                showFavorite = true
            )

            instance.selectFriendPopup(
                context = context,
                params = openPickerFriendRequestParams
            ) { selectedUsers, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else {
                    val user = selectedUsers?.users?.firstOrNull()
                    val name = user?.profileNickname ?: ""
                    val image = user?.profileThumbnailImage ?: ""
                    if (name.isEmpty() || image.isEmpty()) {
                        continuation.resumeWithException(RuntimeException("Can't find user"))
                    } else {
                        continuation.resume(
                            KakaoFriendInfo(
                                name = name,
                                profileImageUrl = image
                            )
                        )
                    }
                }
            }
        }
    }
}
