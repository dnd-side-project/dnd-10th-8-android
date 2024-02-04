package ac.dnd.bookkeeping.android.presentation.model.login

import ac.dnd.bookkeeping.android.domain.model.authentication.sociallogin.KakaoUserInformation
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KakaoUserInformationModel(
    val socialId: Long,
    val email: String,
    val name: String,
    val profileImageUrl: String
) : Parcelable

fun KakaoUserInformation.toUiModel(): KakaoUserInformationModel {
    return KakaoUserInformationModel(
        socialId = socialId,
        email = email,
        name = name,
        profileImageUrl = profileImageUrl
    )
}
