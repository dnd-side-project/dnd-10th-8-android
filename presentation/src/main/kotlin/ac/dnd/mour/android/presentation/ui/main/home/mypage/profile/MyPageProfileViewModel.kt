package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.member.Profile
import ac.dnd.mour.android.domain.usecase.member.EditProfileUseCase
import ac.dnd.mour.android.domain.usecase.member.EditProfileWithUploadUseCase
import ac.dnd.mour.android.domain.usecase.member.GetProfileUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyPageProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val editProfileUseCase: EditProfileUseCase,
    private val editProfileWithUploadUseCase: EditProfileWithUploadUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageProfileState> =
        MutableStateFlow(MyPageProfileState.Init)
    val state: StateFlow<MyPageProfileState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageProfileEvent> = MutableEventFlow()
    val event: EventFlow<MyPageProfileEvent> = _event.asEventFlow()

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(
        Profile(
            id = 0L,
            email = "",
            profileImageUrl = "",
            name = "",
            nickname = "",
            gender = "",
            birth = LocalDate(2020, 1, 1)
        )
    )
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    init {
        getProfile()
    }

    fun onIntent(intent: MyPageProfileIntent) {
        when (intent) {
            is MyPageProfileIntent.OnEdit -> {
                if (intent.imageName.isEmpty()) {
                    editProfile(intent.profile)
                } else {
                    editProfileWithUpload(
                        profile = intent.profile,
                        fileName = intent.imageName
                    )
                }
            }
        }
    }

    private fun getProfile() {
        launch {
            _state.value = MyPageProfileState.Loading
            getProfileUseCase()
                .onSuccess {
                    _state.value = MyPageProfileState.Init
                    _profile.value = it
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

    private fun editProfile(profile: Profile) {
        launch {
            _state.value = MyPageProfileState.Loading
            editProfileUseCase(
                profileImageUrl = profile.profileImageUrl,
                nickname = profile.nickname,
                gender = profile.gender,
                birth = profile.birth
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
        profile: Profile,
        fileName: String
    ) {
        launch {
            _state.value = MyPageProfileState.Loading
            editProfileWithUploadUseCase(
                profileImageUri = profile.profileImageUrl,
                profileImageName = fileName,
                nickname = profile.nickname,
                gender = profile.gender,
                birth = profile.birth
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
