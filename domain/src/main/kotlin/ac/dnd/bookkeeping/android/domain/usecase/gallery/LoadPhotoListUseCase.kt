package ac.dnd.bookkeeping.android.domain.usecase.gallery

import ac.dnd.bookkeeping.android.domain.repository.GalleryImageRepository
import androidx.paging.PagingData
import com.dnd_9th_3_android.gooding.model.record.GalleryImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadPhotoListUseCase @Inject constructor(
    private val galleryImageRepository: GalleryImageRepository
) {
    operator fun invoke(
        currentFolder: Pair<String, String?>
    ): Flow<PagingData<GalleryImage>> {
        return galleryImageRepository.getPagingPhotos(currentFolder)
    }
}
