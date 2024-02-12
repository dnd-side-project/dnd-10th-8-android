package ac.dnd.bookkeeping.android.data.remote.network.model.schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditScheduleReq(
    @SerialName("relationId")
    val relationId: Long,
    @SerialName("day")
    val day: LocalDate,
    @SerialName("event")
    val event: String,
    @SerialName("repeatType")
    val repeatType: String,
    @SerialName("repeatFinish")
    val repeatFinish: LocalDate?,
    @SerialName("alarm")
    val alarm: LocalDateTime?,
    @SerialName("time")
    val time: LocalTime?,
    @SerialName("link")
    val link: String,
    @SerialName("location")
    val location: String,
    @SerialName("memo")
    val memo: String
)
