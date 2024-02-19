package ac.dnd.mour.android.domain.model.authentication.sociallogin

data class KakaoUserInformation(
    val socialId: Long,
    val email: String,
    val name: String,
    val profileImageUrl: String
)
