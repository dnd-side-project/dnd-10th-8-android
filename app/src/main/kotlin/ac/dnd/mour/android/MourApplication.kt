package ac.dnd.mour.android

import ac.dnd.mour.android.domain.repository.TokenRepository
import ac.dnd.mour.android.presentation.common.CHANNEL_1
import ac.dnd.mour.android.presentation.common.CHANNEL_GROUP_1
import ac.dnd.mour.android.presentation.ui.invalid.InvalidJwtTokenActivity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.KakaoSdk
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig
import dagger.hilt.android.HiltAndroidApp
import io.sentry.Sentry
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltAndroidApp
open class MourApplication : Application() {

    @Inject
    lateinit var tokenRepository: TokenRepository

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.d(exception)
        Sentry.captureException(exception)
        Firebase.crashlytics.recordException(exception)
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initializeChannel(this)
        }

        initializeFirebase()
        initializeClarity()
        observeRefreshTokenValidation()
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

    private fun initializeFirebase() {
        Firebase.analytics
    }

    private fun initializeClarity() {
        val config = ClarityConfig(getString(R.string.clarity_key))
        Clarity.initialize(applicationContext, config)
    }

    private fun observeRefreshTokenValidation() {
        with(ProcessLifecycleOwner.get()) {
            lifecycleScope.launch(handler) {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    tokenRepository.isRefreshTokenInvalid.collect { isRefreshTokenInvalid ->
                        if (isRefreshTokenInvalid) {
                            val intent = Intent(
                                this@MourApplication,
                                InvalidJwtTokenActivity::class.java
                            ).apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            startActivity(intent)
                            tokenRepository.resetRefreshTokenInvalidFlag()
                        }
                    }
                }
            }
        }
    }
}
