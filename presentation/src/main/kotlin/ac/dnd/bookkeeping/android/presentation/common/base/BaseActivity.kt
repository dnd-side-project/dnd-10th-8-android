package ac.dnd.bookkeeping.android.presentation.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObserver()
    }

    protected open fun initView() = Unit

    protected open fun initObserver() = Unit
}
