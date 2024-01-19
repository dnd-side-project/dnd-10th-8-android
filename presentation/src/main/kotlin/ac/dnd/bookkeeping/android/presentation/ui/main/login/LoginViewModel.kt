package ac.dnd.bookkeeping.android.presentation.ui.main.login

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel()
