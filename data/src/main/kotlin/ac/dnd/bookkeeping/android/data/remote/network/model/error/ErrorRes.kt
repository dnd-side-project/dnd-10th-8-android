package ac.dnd.bookkeeping.android.data.remote.network.model.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorRes(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String
)
