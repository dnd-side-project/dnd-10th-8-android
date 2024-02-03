package ac.dnd.bookkeeping.android.data.remote.network.model.heart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EditHeartReq(
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