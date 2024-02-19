package ac.dnd.mour.android.data.remote.network.model.heart

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddHeartReq(
    @SerialName("relationId")
    val relationId: Long,
    @SerialName("give")
    val give: Boolean,
    @SerialName("money")
    val money: Long,
    @SerialName("day")
    val day: LocalDate,
    @SerialName("event")
    val event: String,
    @SerialName("memo")
    val memo: String,
    @SerialName("tags")
    val tags: List<String>
)
