package ac.dnd.bookkeeping.android.presentation.common.util

import ac.dnd.bookkeeping.android.presentation.R
import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.gun0912.tedpermission.coroutine.TedPermission

@SuppressLint("MissingPermission")
suspend fun Context.showNotification(
    channelId: String,
    notificationId: Int = 0,
    title: String? = null,
    content: String? = null,
    @DrawableRes icon: Int = R.drawable.ic_launcher,
    group: String? = null,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
): Boolean {
    if (!checkPermissionGranted() || !checkNotificationChannelEnabled(channelId)) return false

    val notification = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(priority)
        .setGroup(group)
        .build()

    NotificationManagerCompat.from(this)
        .notify(notificationId, notification)

    return true
}

private suspend fun checkPermissionGranted(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        TedPermission
            .create()
            .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
            .check().isGranted
    } else {
        false
    }
}

private suspend fun Context.checkNotificationChannelEnabled(
    id: String
): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val manager = getSystemService(NotificationManager::class.java)
        val channel = manager.getNotificationChannel(id) ?: return false
        val isChannelEnabled = channel.importance != NotificationManager.IMPORTANCE_NONE

        if (!isChannelEnabled) {
            val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                putExtra(Settings.EXTRA_CHANNEL_ID, id)
            }
            startActivity(intent)
        }
        return isChannelEnabled
    } else {
        true
    }
}
