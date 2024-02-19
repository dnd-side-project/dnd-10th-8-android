package ac.dnd.mour.android.data.remote.network.model.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterReq(
    @SerialName("socialId")
    val socialId: Long,
    @SerialName("email")
    val email: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("name")
    val name: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("birth")
    val birth: String
)
