package ac.dnd.bookkeeping.android.presentation.common.util.alarm

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Alarm
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.todayIn

fun registerAlarm(
    context: Context,
    alarmList: List<Alarm>
) {
    val alarmManager = context.getSystemService(AlarmManager::class.java) ?: return
    val now = Clock.System.now()

    alarmList.forEach { alarm ->
        if (alarm.alarm.toInstant(TimeZone.currentSystemDefault()) < now) return@forEach
        val intent = alarm.toIntent(context)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            alarm.alarm.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
            intent
        )
    }
}

fun unregisterAlarm(
    context: Context,
    alarmList: List<Alarm>
) {
    val alarmManager = context.getSystemService(AlarmManager::class.java) ?: return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        alarmManager.cancelAll()
    } else {
        alarmList.forEach { alarm ->
            val intent = alarm.toIntent(context)

            alarmManager.cancel(intent)
        }
    }
}

private fun Alarm.toIntent(context: Context): PendingIntent {
    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())

    val formattedTime = time?.let {
        val fixedHour = if (it.hour == 0) 24 else it.hour
        val timeHour = (fixedHour - 1) % 12 + 1
        val timeMinute = it.minute
        val timeAmPm = if (fixedHour < 12) "오전" else "오후"
        val format = "%s %02d:%02d"
        runCatching {
            String.format(format, timeAmPm, timeHour, timeMinute)
        }.getOrDefault("시간 없음")
    } ?: "시간 없음"

    val formattedDate = (day - now).days.let {
        when (it) {
            0 -> "오늘"
            1 -> "내일"
            2 -> "모레"
            in 3..6 -> "${it}일 후"
            else -> "${it / 7}주 후"
        }
    }

    val title = "경사 미리알림"
    val content = if (time == null) {
        "$formattedDate ${relation.name}님의 $event 일정이 있습니다."
    } else {
        "$formattedDate ${formattedTime}에 ${relation.name}님의 $event 일정이 있습니다."
    }

    return Intent(context, AlarmReceiver::class.java).apply {
        putExtra(AlarmReceiver.NOTIFICATION_ID, id.toInt())
        putExtra(AlarmReceiver.NOTIFICATION_TITLE, title)
        putExtra(AlarmReceiver.NOTIFICATION_CONTENT, content)
    }.let { intent ->
        PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
