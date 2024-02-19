package ac.dnd.mour.android.data.remote.network.model.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginReq(
    @SerialName("socialId")
    val socialId: Long,
    @SerialName("email")
    val email: String
)
