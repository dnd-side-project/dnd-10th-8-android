package ac.dnd.bookkeeping.android.presentation.common.view.gallery

import ac.dnd.bookkeeping.android.domain.usecase.gallery.LoadFolderListUseCase
import ac.dnd.bookkeeping.android.domain.usecase.gallery.LoadPhotoListUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import com.dnd_9th_3_android.gooding.model.record.GalleryImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val loadFolderListUseCase: LoadFolderListUseCase,
    private val loadPhotoListUseCase: LoadPhotoListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<GalleryState> =
        MutableStateFlow(GalleryState.Init)
    val state: StateFlow<GalleryState> = _state.asStateFlow()

    private val _galleryPhotoList = MutableStateFlow<List<GalleryImage>>(emptyList())
    val galleryPhotoList: StateFlow<List<GalleryImage>> = _galleryPhotoList.asStateFlow()

    private val _folders: MutableStateFlow<List<Pair<String, String?>>> =
        MutableStateFlow(listOf("최근 항목" to null))
    val folders: StateFlow<List<Pair<String, String?>>> = _folders.asStateFlow()

    private val _currentFolder = MutableStateFlow<Pair<String, String?>>("최근 항목" to null)
    val currentFolder: StateFlow<Pair<String, String?>> = _currentFolder

    private fun getGalleryPagingImages() {
        launch {
            _galleryPhotoList.emit(loadPhotoListUseCase())
        }
    }

    private fun getFolders() {
        launch {
            _folders.emit(
                loadFolderListUseCase().map {
                    it.split("/").last() to it
                }
            )
        }
    }

    fun setCurrentFolder(location: Pair<String, String?>) {
        launch {
            _currentFolder.emit(location)
        }
    }

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
        getGalleryPagingImages()
        getFolders()
    }
}
