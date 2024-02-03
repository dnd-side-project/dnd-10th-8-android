package ac.dnd.bookkeeping.android.data.remote.network.model.schedule

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.model.schedule.UnrecordedScheduleRelation
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUnrecordedScheduleListRes(
    @SerialName("result")
    val result: List<GetUnrecordedScheduleItemRes>
) : DataMapper<List<UnrecordedSchedule>> {
    override fun toDomain(): List<UnrecordedSchedule> {
        return result.map { it.toDomain() }
    }
}

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
) : DataMapper<UnrecordedSchedule> {
    override fun toDomain(): UnrecordedSchedule {
        return UnrecordedSchedule(
            id = id,
            relation = relation.toDomain(),
            day = day,
            event = event,
            alarm = alarm,
            time = time,
            link = link,
            location = location
        )
    }
}

@Serializable
data class GetUnrecordedScheduleItemRelationRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<UnrecordedScheduleRelation> {
    override fun toDomain(): UnrecordedScheduleRelation {
        return UnrecordedScheduleRelation(
            id = id,
            name = name
        )
    }
}
