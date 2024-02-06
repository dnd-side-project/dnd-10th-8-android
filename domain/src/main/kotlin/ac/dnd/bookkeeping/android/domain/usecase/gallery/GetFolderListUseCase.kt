package ac.dnd.bookkeeping.android.domain.usecase.gallery

import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryFolder
import ac.dnd.bookkeeping.android.domain.repository.GalleryRepository
import javax.inject.Inject

class GetFolderListUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    operator fun invoke(): List<GalleryFolder> {
        return galleryRepository.getFolderList()
    }
}
