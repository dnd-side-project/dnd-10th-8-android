package ac.dnd.mour.android.domain.usecase.file

import javax.inject.Inject

class GetUrlAndUploadImageUseCase @Inject constructor(
    private val getPreSignedUrlUseCase: GetPreSignedUrlUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) {
    suspend operator fun invoke(
        imageUri: String,
        fileName: String
    ): Result<String> {
        return getPreSignedUrlUseCase(
            fileName = fileName
        ).map { preSignedUrl ->
            uploadImageUseCase(
                preSignedUrl = preSignedUrl.preSignedUrl,
                imageUri = imageUri
            ).getOrThrow()

            preSignedUrl.uploadFileUrl
        }
    }
}
