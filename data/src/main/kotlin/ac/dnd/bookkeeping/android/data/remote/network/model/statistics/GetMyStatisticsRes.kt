package ac.dnd.bookkeeping.android.data.remote.network.model.statistics

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatisticsEvent
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatisticsGive
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatisticsTake
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyStatisticsRes(
    @SerialName("give")
    val give: GetMyStatisticsGiveRes,
    @SerialName("take")
    val take: GetMyStatisticsTakeRes
) : DataMapper<MyStatistics> {
    override fun toDomain(): MyStatistics {
        return MyStatistics(
            give = give.toDomain(),
            take = take.toDomain()
        )
    }
}

@Serializable
data class GetMyStatisticsGiveRes(
    @SerialName("event")
    val event: List<GetMyStatisticsEventRes>
) : DataMapper<MyStatisticsGive> {
    override fun toDomain(): MyStatisticsGive {
        return MyStatisticsGive(
            event = event.map { it.toDomain() }
        )
    }
}

@Serializable
data class GetMyStatisticsTakeRes(
    @SerialName("event")
    val event: List<GetMyStatisticsEventRes>
) : DataMapper<MyStatisticsTake> {
    override fun toDomain(): MyStatisticsTake {
        return MyStatisticsTake(
            event = event.map { it.toDomain() }
        )
    }
}

@Serializable
data class GetMyStatisticsEventRes(
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: String,
    @SerialName("money")
    val money: Long,
    @SerialName("day")
    val day: LocalDate
) : DataMapper<MyStatisticsEvent> {
    override fun toDomain(): MyStatisticsEvent {
        return MyStatisticsEvent(
            name = name,
            group = group,
            money = money,
            day = day
        )
    }
}
