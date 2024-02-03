package ac.dnd.bookkeeping.android.data.remote.network.model.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddGroupRes(
    @SerialName("result")
    val result: Long
)
