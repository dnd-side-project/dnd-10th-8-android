package ac.dnd.bookkeeping.android.presentation.ui.main.kakao_login_sample

import ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin.SocialLoginUseCases
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val socialLoginUseCases: SocialLoginUseCases
) : BaseViewModel() {

    fun login() = launch {
        socialLoginUseCases.kakaoLoginUseCase.invoke()
            .onSuccess {
                getUserInfo()
                Timber.d("user token(for check login state): $it")
            }
            .onFailure {
                Timber.d("login error: ${it.message}")
            }
    }

    private fun getUserInfo() = launch {
        socialLoginUseCases.getUserInfoUseCase.invoke()
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
