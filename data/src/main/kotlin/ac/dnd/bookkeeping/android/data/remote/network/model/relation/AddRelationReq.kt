package ac.dnd.bookkeeping.android.data.remote.network.model.relation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRelationReq(
    @SerialName("groupId")
    val groupId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("memo")
    val memo: String,
)
