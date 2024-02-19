package ac.dnd.mour.android.data.remote.network.model.schedule

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimple
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimpleGroup
import ac.dnd.mour.android.domain.model.feature.schedule.Schedule
import kotlinx.datetime.LocalDate
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
    val location: String = "",
    @SerialName("memo")
    val memo: String = ""
) : DataMapper<Schedule> {
    override fun toDomain(): Schedule {
        return Schedule(
            id = id,
            relation = relation.toDomain(),
            day = day,
            event = event,
            repeatType = null,
            repeatFinish = null,
            alarm = null,
            time = time,
            link = link,
            location = location,
            memo = memo
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
) : DataMapper<RelationSimple> {
    override fun toDomain(): RelationSimple {
        return RelationSimple(
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
) : DataMapper<RelationSimpleGroup> {
    override fun toDomain(): RelationSimpleGroup {
        return RelationSimpleGroup(
            id = id,
            name = name
        )
    }
}
