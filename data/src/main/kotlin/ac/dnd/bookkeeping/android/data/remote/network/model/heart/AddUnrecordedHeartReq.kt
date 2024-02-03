package ac.dnd.bookkeeping.android.data.remote.network.model.heart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddUnrecordedHeartReq(
    @SerialName("scheduleId")
    val scheduleId: Long,
    @SerialName("money")
    val money: Long,
    @SerialName("tags")
    val tags: List<String>
)
