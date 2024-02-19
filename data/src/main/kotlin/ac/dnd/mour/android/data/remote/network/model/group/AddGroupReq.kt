package ac.dnd.mour.android.data.remote.network.model.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddGroupReq(
    @SerialName("name")
    val name: String
)
