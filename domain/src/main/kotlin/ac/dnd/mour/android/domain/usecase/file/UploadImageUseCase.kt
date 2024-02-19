package ac.dnd.mour.android.domain.usecase.file

import ac.dnd.mour.android.domain.repository.FileRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(
        preSignedUrl: String,
        imageUri: String
    ): Result<Unit> {
        return fileRepository.upload(
            preSignedUrl = preSignedUrl,
            imageUri = imageUri
        )
    }
}
