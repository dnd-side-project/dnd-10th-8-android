package ac.dnd.bookkeeping.android.domain.model.authentication.sociallogin

data class KakaoUserInformation(
    val socialId: Long,
    val email: String,
    val name: String,
    val profileImageUrl: String
)
