package ac.dnd.bookkeeping.android.data.remote.network.model.relation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRelationRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetRelationGroupRes,
    @SerialName("giveMoney")
    val giveMoney: Long,
    @SerialName("takeMoney")
    val takeMoney: Long
)

@Serializable
data class GetRelationGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)

