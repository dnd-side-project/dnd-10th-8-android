package ac.dnd.bookkeeping.android.data.remote.network.model.statistics

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyStatisticsRes(
    @SerialName("give")
    val give: GetMyStatisticsGiveRes,
    @SerialName("take")
    val take: GetMyStatisticsTakeRes
)

@Serializable
data class GetMyStatisticsGiveRes(
    @SerialName("event")
    val event: List<GetMyStatisticsEventRes>
)

@Serializable
data class GetMyStatisticsTakeRes(
    @SerialName("event")
    val event: List<GetMyStatisticsEventRes>
)

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
)
