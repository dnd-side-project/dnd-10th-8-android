package ac.dnd.bookkeeping.android.domain.usecase.gallery

import ac.dnd.bookkeeping.android.domain.repository.GalleryImageRepository
import androidx.paging.PagingData
import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoListUseCase @Inject constructor(
    private val galleryImageRepository: GalleryImageRepository
) {
    operator fun invoke(
        currentFolder: Pair<String, String?>
    ): Flow<PagingData<GalleryImage>> {
        return galleryImageRepository.getPagingPhotos(currentFolder)
    }
}
