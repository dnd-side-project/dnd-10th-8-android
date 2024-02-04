package ac.dnd.bookkeeping.android

import ac.dnd.bookkeeping.android.presentation.common.CHANNEL_1
import ac.dnd.bookkeeping.android.presentation.common.CHANNEL_GROUP_1
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class BookkeepingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initializeChannel(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeChannel(
        context: Context
    ) {
        val channel1 = NotificationChannel(
            CHANNEL_1,
            "channel 1",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "This is channel 1"
        }

        val group = NotificationChannelGroup(
            CHANNEL_GROUP_1,
            "group 1"
        ).apply {
            channels.add(channel1)
        }

        context.getSystemService(NotificationManager::class.java).run {
            createNotificationChannel(channel1)
            createNotificationChannelGroup(group)
        }
    }
}
