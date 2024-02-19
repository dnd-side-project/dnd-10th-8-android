package ac.dnd.mour.android.domain.usecase.gallery

import ac.dnd.mour.android.domain.model.gallery.GalleryFolder
import ac.dnd.mour.android.domain.repository.GalleryRepository
import javax.inject.Inject

class GetFolderListUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    operator fun invoke(): List<GalleryFolder> {
        return galleryRepository.getFolderList()
    }
}
