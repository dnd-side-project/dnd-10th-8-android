package ac.dnd.bookkeeping.android.presentation.ui.main.kakao_login_sample

import ac.dnd.bookkeeping.android.domain.repository.social_login.SocialLoginRepository
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val socialLoginRepository: SocialLoginRepository
) : BaseViewModel() {

    fun login() = launch {
        socialLoginRepository.login()
            .onSuccess {
                getUserInfo()
                Timber.d("user token(for check login state): $it")
            }
            .onFailure {
                Timber.d("login error: ${it.message}")
            }
    }

    private fun getUserInfo() = launch {
        socialLoginRepository.getUserInfo()
            .onSuccess {

                Timber.d("user email: ${it.email}")
                Timber.d("user id: ${it.socialId}")
                Timber.d("user name: ${it.name}")
            }
            .onFailure {
                Timber.d("login error: ${it.message}")
            }
    }
}
