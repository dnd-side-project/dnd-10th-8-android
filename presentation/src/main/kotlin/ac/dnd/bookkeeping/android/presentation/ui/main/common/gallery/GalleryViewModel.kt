package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery

import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryFolder
import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryImage
import ac.dnd.bookkeeping.android.domain.usecase.gallery.GetFolderListUseCase
import ac.dnd.bookkeeping.android.domain.usecase.gallery.GetPhotoListUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFolderListUseCase: GetFolderListUseCase,
    private val getPhotoListUseCase: GetPhotoListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<GalleryState> =
        MutableStateFlow(GalleryState.Init)
    val state: StateFlow<GalleryState> = _state.asStateFlow()

    private val _galleryPhotoList = MutableStateFlow<PagingData<GalleryImage>>(PagingData.empty())
    val galleryPhotoList: StateFlow<PagingData<GalleryImage>> = _galleryPhotoList.asStateFlow()

    private val _folders: MutableStateFlow<List<GalleryFolder>> =
        MutableStateFlow(listOf(GalleryFolder("최근 항목", null)))
    val folders: StateFlow<List<GalleryFolder>> = _folders.asStateFlow()

    private val _currentFolder = MutableStateFlow<GalleryFolder>(GalleryFolder("최근 항목", null))
    val currentFolder: StateFlow<GalleryFolder> = _currentFolder

    fun onIntent(intent: GalleryIntent) {
        when (intent) {
            is GalleryIntent.OnGrantPermission -> loadData()
            is GalleryIntent.OnChangeFolder -> {
                setCurrentFolder(intent.location)
                getGalleryPagingImages()
            }
        }
    }

    private fun loadData() {
        getFolders()
        getGalleryPagingImages()
    }

    private fun getGalleryPagingImages() {
        launch {
            getPhotoListUseCase(currentFolder.value)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _galleryPhotoList.value = it
                }
        }
    }

    private fun getFolders() {
        _folders.value = getFolderListUseCase()
    }

    private fun setCurrentFolder(foloder: GalleryFolder) {
        _currentFolder.value = foloder
    }
}
