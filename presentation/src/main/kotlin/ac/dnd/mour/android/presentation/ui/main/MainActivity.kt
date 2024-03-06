package ac.dnd.mour.android.presentation.ui.main

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.base.BaseActivity
import androidx.activity.compose.setContent
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun initView() {
        setContent {
            MainScreen()
        }
    }

    override fun initObserver() {
        initializeClarity()
    }

    private fun initializeClarity() {
        val config = ClarityConfig(getString(R.string.clarity_key))
        Clarity.initialize(applicationContext, config)
    }
}
