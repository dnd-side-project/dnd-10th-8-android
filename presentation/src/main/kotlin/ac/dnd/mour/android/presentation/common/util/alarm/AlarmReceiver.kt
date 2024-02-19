package ac.dnd.mour.android.presentation.common.util.alarm

import ac.dnd.mour.android.common.orZero
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.CHANNEL_1
import ac.dnd.mour.android.presentation.common.CHANNEL_GROUP_1
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationId = intent?.getIntExtra(NOTIFICATION_ID, 0).orZero()
        val title = intent?.getStringExtra(NOTIFICATION_TITLE).orEmpty()
        val content = intent?.getStringExtra(NOTIFICATION_CONTENT).orEmpty()
        val channelId = CHANNEL_1
        val icon = R.drawable.ic_launcher
        val group = CHANNEL_GROUP_1
        val priority = NotificationCompat.PRIORITY_DEFAULT

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(priority)
            .setGroup(group)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }

    companion object {
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION_TITLE = "notification_title"
        const val NOTIFICATION_CONTENT = "notification_content"
    }
}
