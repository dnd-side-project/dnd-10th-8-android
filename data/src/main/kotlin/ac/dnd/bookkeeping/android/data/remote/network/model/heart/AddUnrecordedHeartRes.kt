package ac.dnd.bookkeeping.android.data.remote.network.model.heart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddUnrecordedHeartRes(
    @SerialName("result")
    val result: Long
)
