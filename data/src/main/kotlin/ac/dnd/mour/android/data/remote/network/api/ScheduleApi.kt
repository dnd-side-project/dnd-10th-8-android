package ac.dnd.mour.android.data.remote.network.api

import ac.dnd.mour.android.data.remote.network.di.AuthHttpClient
import ac.dnd.mour.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.mour.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.mour.android.data.remote.network.model.schedule.AddScheduleReq
import ac.dnd.mour.android.data.remote.network.model.schedule.AddScheduleRes
import ac.dnd.mour.android.data.remote.network.model.schedule.EditScheduleReq
import ac.dnd.mour.android.data.remote.network.model.schedule.GetAlarmListRes
import ac.dnd.mour.android.data.remote.network.model.schedule.GetScheduleListRes
import ac.dnd.mour.android.data.remote.network.model.schedule.GetScheduleRes
import ac.dnd.mour.android.data.remote.network.model.schedule.GetUnrecordedScheduleListRes
import ac.dnd.mour.android.data.remote.network.util.convert
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class ScheduleApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun addSchedule(
        relationId: Long,
        day: LocalDate,
        event: String,
        repeatType: String,
        repeatFinish: LocalDate?,
        alarm: LocalDateTime?,
        time: LocalTime?,
        link: String,
        location: String,
        memo: String
    ): Result<AddScheduleRes> {
        return client.post("$baseUrl/api/v1/schedules") {
            setBody(
                AddScheduleReq(
                    relationId = relationId,
                    day = day,
                    event = event,
                    repeatType = repeatType,
                    repeatFinish = repeatFinish,
                    alarm = alarm,
                    time = time,
                    link = link,
                    location = location,
                    memo = memo
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun editSchedule(
        id: Long,
        day: LocalDate,
        event: String,
        repeatType: String,
        repeatFinish: LocalDate?,
        alarm: LocalDateTime?,
        time: LocalTime?,
        link: String,
        location: String,
        memo: String
    ): Result<Unit> {
        return client.patch("$baseUrl/api/v1/schedules/$id") {
            setBody(
                EditScheduleReq(
                    day = day,
                    event = event,
                    repeatType = repeatType,
                    repeatFinish = repeatFinish,
                    alarm = alarm,
                    time = time,
                    link = link,
                    location = location,
                    memo = memo
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun deleteSchedule(
        id: Long,
    ): Result<Unit> {
        return client.delete("$baseUrl/api/v1/schedules/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun getUnrecordedScheduleList(
        name: String
    ): Result<GetUnrecordedScheduleListRes> {
        return client.get("$baseUrl/api/v1/schedules/unrecorded") {
            parameter("name", name)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getSchedule(
        id: Long
    ): Result<GetScheduleRes> {
        return client.get("$baseUrl/api/v1/schedules/me/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun getScheduleList(
        year: Int,
        month: Int
    ): Result<GetScheduleListRes> {
        return client.get("$baseUrl/api/v1/schedules/me") {
            parameter("year", year)
            parameter("month", month)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getAlarmList(): Result<GetAlarmListRes> {
        return client.get("$baseUrl/api/v1/schedules/me/alarm")
            .convert(errorMessageMapper::map)
    }
}
