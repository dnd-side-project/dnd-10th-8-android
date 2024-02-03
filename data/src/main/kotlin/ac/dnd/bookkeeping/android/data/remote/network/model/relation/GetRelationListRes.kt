package ac.dnd.bookkeeping.android.data.remote.network.model.relation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRelationListRes(
    @SerialName("result")
    val result: List<GetRelationItemRes>
)

@Serializable
data class GetRelationItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetRelationItemGroupRes,
)

@Serializable
data class GetRelationItemGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)

