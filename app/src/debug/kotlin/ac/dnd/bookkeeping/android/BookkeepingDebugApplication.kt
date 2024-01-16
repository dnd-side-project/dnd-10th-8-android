package ac.dnd.bookkeeping.android

import timber.log.Timber

class BookkeepingDebugApplication : BookkeepingApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
