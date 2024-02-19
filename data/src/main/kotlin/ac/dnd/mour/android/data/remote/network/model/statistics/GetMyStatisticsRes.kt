package ac.dnd.mour.android.data.remote.network.model.statistics

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.mour.android.domain.model.feature.statistics.MyStatisticsItem
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyStatisticsRes(
    @SerialName("give")
    val give: List<GetMyStatisticsItemRes>,
    @SerialName("take")
    val take: List<GetMyStatisticsItemRes>
) : DataMapper<MyStatistics> {
    override fun toDomain(): MyStatistics {
        return MyStatistics(
            give = give.map { it.toDomain() },
            take = take.map { it.toDomain() }
        )
    }
}

@Serializable
data class GetMyStatisticsItemRes(
    @SerialName("event")
    val event: String,
    @SerialName("relationName")
    val relationName: String,
    @SerialName("groupName")
    val groupName: String,
    @SerialName("money")
    val money: Long,
    @SerialName("day")
    val day: LocalDate,
    @SerialName("memo")
    val memo: String
) : DataMapper<MyStatisticsItem> {
    override fun toDomain(): MyStatisticsItem {
        return MyStatisticsItem(
            event = event,
            relationName = relationName,
            groupName = groupName,
            money = money,
            day = day,
            memo = memo
        )
    }
}
