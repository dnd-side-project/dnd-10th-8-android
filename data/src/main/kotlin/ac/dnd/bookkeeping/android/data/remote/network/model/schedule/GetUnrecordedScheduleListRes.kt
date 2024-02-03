package ac.dnd.bookkeeping.android.data.remote.network.model.schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUnrecordedScheduleListRes(
    @SerialName("result")
    val result: List<GetUnrecordedScheduleItemRes>
)

@Serializable
data class GetUnrecordedScheduleItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("relation")
    val relation: GetUnrecordedScheduleItemRelationRes,
    @SerialName("day")
    val day: LocalDate,
    @SerialName("event")
    val event: String,
    @SerialName("alarm")
    val alarm: LocalDateTime,
    @SerialName("time")
    val time: LocalTime,
    @SerialName("link")
    val link: String,
    @SerialName("location")
    val location: String
)

@Serializable
data class GetUnrecordedScheduleItemRelationRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)

