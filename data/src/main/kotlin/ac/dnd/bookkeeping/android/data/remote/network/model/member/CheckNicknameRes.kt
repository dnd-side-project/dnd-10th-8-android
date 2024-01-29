package ac.dnd.bookkeeping.android.data.remote.network.model.member

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameRes(
    @SerialName("result")
    val result: Boolean
)
