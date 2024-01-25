package ac.dnd.bookkeeping.android.presentation.ui.main.registration

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val savedStateHiltViewModel: HiltViewModel
): BaseViewModel()
