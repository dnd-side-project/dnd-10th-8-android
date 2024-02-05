package ac.dnd.bookkeeping.android.domain.usecase.gallery

import com.dnd_9th_3_android.gooding.model.record.GalleryImage
import javax.inject.Inject

class LoadPhotoListUseCase @Inject constructor(

) {
    suspend operator fun invoke(): List<GalleryImage> {
        return emptyList()
    }
}
