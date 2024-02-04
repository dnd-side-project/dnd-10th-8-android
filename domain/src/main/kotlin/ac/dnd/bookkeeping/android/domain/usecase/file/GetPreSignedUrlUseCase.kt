package ac.dnd.bookkeeping.android.domain.usecase.file

import ac.dnd.bookkeeping.android.domain.model.file.PreSignedUrl
import ac.dnd.bookkeeping.android.domain.repository.FileRepository
import javax.inject.Inject

class GetPreSignedUrlUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(
        fileName: String
    ): Result<PreSignedUrl> {
        return fileRepository.getPreSignedUrl(
            fileName = fileName
        )
    }
}
