package ac.dnd.bookkeeping.android.data.remote.network.model.schedule

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelationGroup
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetScheduleListRes(
    @SerialName("result")
    val result: List<GetScheduleItemRes>
) : DataMapper<List<Schedule>> {
    override fun toDomain(): List<Schedule> {
        return result.map { it.toDomain() }
    }
}

@Serializable
data class GetScheduleItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("relation")
    val relation: GetScheduleItemRelationRes,
    @SerialName("day")
    val day: LocalDate,
    @SerialName("event")
    val event: String,
    @SerialName("time")
    val time: LocalTime? = null,
    @SerialName("link")
    val link: String = "",
    @SerialName("location")
    val location: String = ""
) : DataMapper<Schedule> {
    override fun toDomain(): Schedule {
        return Schedule(
            id = id,
            relation = relation.toDomain(),
            day = day,
            event = event,
            time = time,
            link = link,
            location = location
        )
    }
}

@Serializable
data class GetScheduleItemRelationRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetScheduleItemRelationGroupRes
) : DataMapper<ScheduleRelation> {
    override fun toDomain(): ScheduleRelation {
        return ScheduleRelation(
            id = id,
            name = name,
            group = group.toDomain()
        )
    }
}

@Serializable
data class GetScheduleItemRelationGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<ScheduleRelationGroup> {
    override fun toDomain(): ScheduleRelationGroup {
        return ScheduleRelationGroup(
            id = id,
            name = name
        )
    }
}
