package ac.dnd.bookkeeping.android.presentation.ui.main.home.bookkeeping

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookkeepingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel()
