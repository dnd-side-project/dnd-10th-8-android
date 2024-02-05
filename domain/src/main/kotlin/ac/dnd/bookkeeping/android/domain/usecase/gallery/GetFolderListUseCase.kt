package ac.dnd.bookkeeping.android.domain.usecase.gallery

import ac.dnd.bookkeeping.android.domain.repository.GalleryImageRepository
import javax.inject.Inject

class GetFolderListUseCase @Inject constructor(
    private val galleryImageRepository : GalleryImageRepository
) {
    operator fun invoke(): List<String> {
        return galleryImageRepository.getFolderList()
    }
}
