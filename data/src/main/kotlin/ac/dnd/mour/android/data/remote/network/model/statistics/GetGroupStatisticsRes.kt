package ac.dnd.mour.android.data.remote.network.model.statistics

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.feature.statistics.GroupStatistics
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGroupStatisticsRes(
    @SerialName("result")
    val result: List<GetGroupStatisticsItemRes>
) : DataMapper<List<GroupStatistics>> {
    override fun toDomain(): List<GroupStatistics> {
        return result.map { it.toDomain() }
    }
}

@Serializable
data class GetGroupStatisticsItemRes(
    @SerialName("event")
    val event: String,
    @SerialName("amount")
    val amount: Long
) : DataMapper<GroupStatistics> {
    override fun toDomain(): GroupStatistics {
        return GroupStatistics(
            event = event,
            amount = amount
        )
    }
}
