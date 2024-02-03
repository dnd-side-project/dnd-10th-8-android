package ac.dnd.bookkeeping.android.data.remote.network.model.member

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditProfileReq(
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("birth")
    val birth: LocalDate
)
