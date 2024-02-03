package ac.dnd.bookkeeping.android.data.remote.network.model.heart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHeartListRes(
    @SerialName("result")
    val result: List<GetHeartItemRes>
)

@Serializable
data class GetHeartItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("relationId")
    val relationId: Long,
    @SerialName("give")
    val give: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetHeartItemGroupRes,
    @SerialName("giveHistories")
    val giveHistories: List<Long>,
    @SerialName("takeHistories")
    val takeHistories: List<Long>
)

@Serializable
data class GetHeartItemGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)

