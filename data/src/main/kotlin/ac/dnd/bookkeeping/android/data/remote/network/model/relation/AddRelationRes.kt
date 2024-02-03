package ac.dnd.bookkeeping.android.data.remote.network.model.relation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRelationRes(
    @SerialName("result")
    val result: Long
)
