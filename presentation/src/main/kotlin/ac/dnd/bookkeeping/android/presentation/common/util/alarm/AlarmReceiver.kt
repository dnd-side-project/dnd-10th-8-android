package ac.dnd.bookkeeping.android.presentation.common.util.alarm

import ac.dnd.bookkeeping.android.common.orZero
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.CHANNEL_1
import ac.dnd.bookkeeping.android.presentation.common.CHANNEL_GROUP_1
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.runBlocking

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getIntExtra(NOTIFICATION_ID, 0).orZero()
        val title = intent?.getStringExtra(NOTIFICATION_TITLE).orEmpty()
        val content = intent?.getStringExtra(NOTIFICATION_CONTENT).orEmpty()
        val channelId = CHANNEL_1
        val icon = R.drawable.ic_launcher
        val group = CHANNEL_GROUP_1
        val priority = NotificationCompat.PRIORITY_DEFAULT

        runBlocking {
            context?.showNotification(
                channelId = channelId,
                notificationId = notificationId,
                title = title,
                content = content,
                icon = icon,
                group = group,
                priority = priority
            )
        }
    }

    companion object {
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION_TITLE = "notification_title"
        const val NOTIFICATION_CONTENT = "notification_content"
    }
}
