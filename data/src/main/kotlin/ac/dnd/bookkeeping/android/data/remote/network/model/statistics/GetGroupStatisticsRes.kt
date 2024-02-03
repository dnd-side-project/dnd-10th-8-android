package ac.dnd.bookkeeping.android.data.remote.network.model.statistics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGroupStatisticsRes(
    @SerialName("marriage")
    val marriage: Long,
    @SerialName("birth")
    val birth: Long,
    @SerialName("baby")
    val baby: Long,
    @SerialName("babyBirth")
    val babyBirth: Long,
    @SerialName("bizopen")
    val bizopen: Long,
    @SerialName("etc")
    val etc: Long,
)
