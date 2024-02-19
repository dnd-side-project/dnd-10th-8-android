package ac.dnd.mour.android.domain.usecase.gallery

import ac.dnd.mour.android.domain.model.gallery.GalleryFolder
import ac.dnd.mour.android.domain.model.gallery.GalleryImage
import ac.dnd.mour.android.domain.repository.GalleryRepository
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPhotoListUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    operator fun invoke(
        currentFolder: GalleryFolder
    ): Flow<PagingData<GalleryImage>> {
        return galleryRepository.getPagingGalleryList(currentFolder)
    }
}
