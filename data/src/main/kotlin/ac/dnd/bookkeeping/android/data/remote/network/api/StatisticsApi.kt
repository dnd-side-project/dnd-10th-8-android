package ac.dnd.bookkeeping.android.data.remote.network.api

import ac.dnd.bookkeeping.android.data.remote.network.di.AuthHttpClient
import ac.dnd.bookkeeping.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.bookkeeping.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.bookkeeping.android.data.remote.network.model.statistics.GetGroupStatisticsRes
import ac.dnd.bookkeeping.android.data.remote.network.model.statistics.GetMyStatisticsRes
import ac.dnd.bookkeeping.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class StatisticsApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getMyStatistics(
        year: Int,
        month: Int? = null
    ): Result<GetMyStatisticsRes> {
        return client.get("$baseUrl/api/v1/statistics/me") {
            parameter("year", year)
            parameter("month", month)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getGroupStatistics(
        gender: String,
        range: Int
    ): Result<GetGroupStatisticsRes> {
        return client.get("$baseUrl/api/v1/statistics/users") {
            parameter("gender", gender) // male, female
            parameter("range", range) // 20, 30, 40, 50
        }.convert(errorMessageMapper::map)
    }
}
