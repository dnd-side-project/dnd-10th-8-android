package ac.dnd.mour.android.data.remote.network.environment

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager

class BaseUrlProvider(
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    fun get(): String {
        // TODO : Add Dev Flag
        val isDev: Boolean = false

        if (isDev) {
            return DEV_BASE_URL
        } else {
            return RELEASE_BASE_URL
        }
    }

    companion object {
        private const val DEV_BASE_URL = "https://43.202.234.130/"
        private const val RELEASE_BASE_URL = "https://43.202.234.130/"
    }
}
