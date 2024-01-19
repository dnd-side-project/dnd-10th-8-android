package ac.dnd.bookkeeping.android.presentation.common.base

import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.ray.rds.window.alert.AlertDialogFragmentProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val viewModel: BaseViewModel

    protected val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        AlertDialogFragmentProvider.makeAlertDialog(
            title = throwable.javaClass.simpleName,
            message = throwable.message
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObserver()
        observeViewModelError()
    }

    protected open fun initView() = Unit

    protected open fun initObserver() = Unit

    private fun observeViewModelError() {
        repeatOnStarted {
            viewModel.errorEvent.eventObserve { event ->
                handler.handleException(viewModel.viewModelScope.coroutineContext, event.throwable)
            }
        }
    }

    fun DialogFragment.show() {
        if (
            !this@BaseActivity.isFinishing
            && !this@BaseActivity.isDestroyed
            && !this@BaseActivity.supportFragmentManager.isDestroyed
            && !this@BaseActivity.supportFragmentManager.isStateSaved
        ) {
            show(this@BaseActivity.supportFragmentManager, javaClass.simpleName)
        }
    }

    protected fun repeatOnStarted(
        block: suspend CoroutineScope.() -> Unit
    ) {
        this.lifecycleScope.launch(handler) {
            repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
}
