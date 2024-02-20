package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.usecase.member.EditProfileUseCase
import ac.dnd.mour.android.domain.usecase.member.EditProfileWithUploadUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.mour.android.presentation.model.mypage.ProfileModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val editProfileUseCase: EditProfileUseCase,
    private val editProfileWithUploadUseCase: EditProfileWithUploadUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageProfileState> =
        MutableStateFlow(MyPageProfileState.Init)
    val state: StateFlow<MyPageProfileState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageProfileEvent> = MutableEventFlow()
    val event: EventFlow<MyPageProfileEvent> = _event.asEventFlow()

    fun onIntent(intent: MyPageProfileIntent) {
        when (intent) {
            is MyPageProfileIntent.OnEdit -> {
                if (intent.imageName.isEmpty()) {
                    editProfile(intent.profile)
                } else {
                    editProfileWithUpload(
                        profileModel = intent.profile,
                        fileName = intent.imageName
                    )
                }
            }
        }
    }

    private fun editProfile(profileModel: ProfileModel) {
        launch {
            _state.value = MyPageProfileState.Loading
            editProfileUseCase(
                profileImageUrl = profileModel.profileImageUrl,
                nickname = profileModel.nickname,
                gender = profileModel.gender,
                birth = profileModel.birth
            )
                .onSuccess {
                    _state.value = MyPageProfileState.Init
                    _event.emit(MyPageProfileEvent.Edit.Success)
                }
                .onFailure { exception ->
                    _state.value = MyPageProfileState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
        }
    }

    private fun editProfileWithUpload(
        profileModel: ProfileModel,
        fileName: String
    ) {
        launch {
            _state.value = MyPageProfileState.Loading
            editProfileWithUploadUseCase(
                profileImageUri = profileModel.profileImageUrl,
                profileImageName = fileName,
                nickname = profileModel.nickname,
                gender = profileModel.gender,
                birth = profileModel.birth
            )
                .onSuccess {
                    _state.value = MyPageProfileState.Init
                    _event.emit(MyPageProfileEvent.Edit.Success)
                }
                .onFailure { exception ->
                    _state.value = MyPageProfileState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
        }
    }
}
