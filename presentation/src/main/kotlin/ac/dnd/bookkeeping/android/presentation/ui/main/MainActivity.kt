package ac.dnd.bookkeeping.android.presentation.ui.main

import androidx.activity.viewModels
import ac.dnd.bookkeeping.android.presentation.databinding.ActivityMainBinding
import ac.dnd.bookkeeping.android.presentation.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        bind {
            vm = viewModel
            lifecycleOwner = this@MainActivity
        }
    }
}
