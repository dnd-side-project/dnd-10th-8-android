package ac.dnd.mour.android

import timber.log.Timber

class MourDebugApplication : MourApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
