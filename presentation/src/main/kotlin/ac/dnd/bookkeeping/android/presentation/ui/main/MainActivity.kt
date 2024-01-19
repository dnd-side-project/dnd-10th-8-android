package ac.dnd.bookkeeping.android.presentation.ui.main

import ac.dnd.bookkeeping.android.presentation.common.base.BaseActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    // TODO : MainScreen 으로 이동
    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        setContent {
            MainScreen()
        }
    }
}
