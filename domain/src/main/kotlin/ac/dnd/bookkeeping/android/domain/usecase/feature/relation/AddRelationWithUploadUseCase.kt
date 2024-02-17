package ac.dnd.bookkeeping.android.domain.usecase.feature.relation

import ac.dnd.bookkeeping.android.domain.usecase.file.GetUrlAndUploadImageUseCase
import javax.inject.Inject

class AddRelationWithUploadUseCase @Inject constructor(
    private val addRelationUseCase: AddRelationUseCase,
    private val getUrlAndUploadImageUseCase: GetUrlAndUploadImageUseCase
) {
    suspend operator fun invoke(
        groupId: Long,
        name: String,
        imageUrl: String,
        imageName: String,
        memo: String
    ): Result<Long> {
        return getUrlAndUploadImageUseCase(
            imageUri = imageUrl,
            fileName = imageName
        ).map { imageUrl ->
            addRelationUseCase(
                groupId = groupId,
                name = name,
                imageUrl = imageUrl,
                memo = memo
            ).getOrThrow()
        }
    }
}
