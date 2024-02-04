package ac.dnd.bookkeeping.android.domain.usecase.feature.relation

import ac.dnd.bookkeeping.android.domain.usecase.file.GetUrlAndUploadImageUseCase
import javax.inject.Inject

class EditRelationWithUploadUseCase @Inject constructor(
    private val editRelationUseCase: EditRelationUseCase,
    private val getUrlAndUploadImageUseCase: GetUrlAndUploadImageUseCase
) {
    suspend operator fun invoke(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        imageName: String,
        memo: String
    ): Result<Unit> {
        return getUrlAndUploadImageUseCase(
            imageUri = imageUrl,
            fileName = imageName
        ).map { imageUrl ->
            editRelationUseCase(
                id = id,
                groupId = groupId,
                name = name,
                imageUrl = imageUrl,
                memo = memo
            ).getOrThrow()
        }
    }
}
