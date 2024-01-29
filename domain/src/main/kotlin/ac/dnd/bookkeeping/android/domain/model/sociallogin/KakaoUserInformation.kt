package ac.dnd.bookkeeping.android.domain.model.sociallogin

data class KakaoUserInformation(
    val socialId: Long,
    val email: String,
    val name: String,
    val profileImageUrl: String
)
