package ac.dnd.bookkeeping.android.data.remote.network.model.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGroupListRes(
    @SerialName("result")
    val result: List<GetGroupItemRes>
)

@Serializable
data class GetGroupItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)
