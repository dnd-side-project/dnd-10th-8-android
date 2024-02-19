package ac.dnd.mour.android.data.repository.gallery

import ac.dnd.mour.android.data.remote.local.gallery.GalleryPagingSource
import ac.dnd.mour.android.domain.model.gallery.GalleryFolder
import ac.dnd.mour.android.domain.model.gallery.GalleryImage
import ac.dnd.mour.android.domain.repository.GalleryImageRepository
import ac.dnd.mour.android.domain.repository.GalleryRepository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val galleryImageRepository: GalleryImageRepository
) : GalleryRepository {
    override fun getPagingGalleryList(folder: GalleryFolder): Flow<PagingData<GalleryImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = GalleryPagingSource.PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GalleryPagingSource(
                    imageRepository = galleryImageRepository,
                    currentLocation = folder.location,
                )
            },
        ).flow
    }

    override fun getFolderList(): List<GalleryFolder> {
        return galleryImageRepository.getFolderList()
    }
}
