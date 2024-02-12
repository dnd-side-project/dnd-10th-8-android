package ac.dnd.bookkeeping.android.data.remote.network.model.schedule

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Alarm
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRelationGroup
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRepeatType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAlarmListRes(
    @SerialName("result")
    val result: List<GetAlarmItemRes>
) : DataMapper<List<Alarm>> {
    override fun toDomain(): List<Alarm> {
        return result.map { it.toDomain() }
    }
}

@Serializable
data class GetAlarmItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("relation")
    val relation: GetAlarmItemRelationRes,
    @SerialName("day")
    val day: LocalDate,
    @SerialName("event")
    val event: String,
    @SerialName("repeatType")
    val repeatType: String = "",
    @SerialName("repeatFinish")
    val repeatFinish: LocalDate? = null,
    @SerialName("alarm")
    val alarm: LocalDateTime,
    @SerialName("time")
    val time: LocalTime? = null,
    @SerialName("link")
    val link: String = "",
    @SerialName("location")
    val location: String = "",
    @SerialName("memo")
    val memo: String = ""
) : DataMapper<Alarm> {
    override fun toDomain(): Alarm {
        return Alarm(
            id = id,
            relation = relation.toDomain(),
            day = day,
            event = event,
            repeatType = AlarmRepeatType.fromValue(repeatType),
            repeatFinish = repeatFinish,
            alarm = alarm,
            time = time,
            link = link,
            location = location,
            memo = memo
        )
    }
}

@Serializable
data class GetAlarmItemRelationRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetAlarmItemRelationGroupRes
) : DataMapper<AlarmRelation> {
    override fun toDomain(): AlarmRelation {
        return AlarmRelation(
            id = id,
            name = name,
            group = group.toDomain()
        )
    }
}

@Serializable
data class GetAlarmItemRelationGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<AlarmRelationGroup> {
    override fun toDomain(): AlarmRelationGroup {
        return AlarmRelationGroup(
            id = id,
            name = name
        )
    }
}
