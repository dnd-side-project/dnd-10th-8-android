package ac.dnd.mour.android.data.remote.network.model.schedule

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedScheduleRelation
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedScheduleRelationGroup
import kotlinx.datetime.LocalDate
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
    @SerialName("time")
    val time: LocalTime? = null,
    @SerialName("link")
    val link: String = "",
    @SerialName("location")
    val location: String = ""
) : DataMapper<UnrecordedSchedule> {
    override fun toDomain(): UnrecordedSchedule {
        return UnrecordedSchedule(
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
data class GetUnrecordedScheduleItemRelationRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetUnrecordedScheduleItemRelationGroupRes
) : DataMapper<UnrecordedScheduleRelation> {
    override fun toDomain(): UnrecordedScheduleRelation {
        return UnrecordedScheduleRelation(
            id = id,
            name = name,
            group = group.toDomain()
        )
    }
}

@Serializable
data class GetUnrecordedScheduleItemRelationGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<UnrecordedScheduleRelationGroup> {
    override fun toDomain(): UnrecordedScheduleRelationGroup {
        return UnrecordedScheduleRelationGroup(
            id = id,
            name = name
        )
    }
}
