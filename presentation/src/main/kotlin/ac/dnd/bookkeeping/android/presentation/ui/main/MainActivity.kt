package ac.dnd.bookkeeping.android.presentation.ui.main

import ac.dnd.bookkeeping.android.presentation.common.base.BaseActivity
import ac.dnd.bookkeeping.android.presentation.ui.main.kakao_login_sample.SampleScreen
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun initView() {
        setContent {
//            MainScreen()
            SampleScreen()
        }
    }
}
