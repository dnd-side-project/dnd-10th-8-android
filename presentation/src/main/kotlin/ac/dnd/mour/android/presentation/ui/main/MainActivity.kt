package ac.dnd.mour.android.presentation.ui.main

import ac.dnd.mour.android.presentation.common.base.BaseActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun initView() {
        setContent {
            MainScreen()
        }
    }
}
