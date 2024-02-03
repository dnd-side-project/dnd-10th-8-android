package ac.dnd.bookkeeping.android.data.remote.network.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddScheduleRes(
    @SerialName("result")
    val result: Long
)
