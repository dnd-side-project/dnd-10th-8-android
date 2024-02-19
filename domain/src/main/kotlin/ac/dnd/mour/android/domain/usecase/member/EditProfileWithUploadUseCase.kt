package ac.dnd.mour.android.domain.usecase.member

import ac.dnd.mour.android.domain.usecase.file.GetUrlAndUploadImageUseCase
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class EditProfileWithUploadUseCase @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val getUrlAndUploadImageUseCase: GetUrlAndUploadImageUseCase
) {
    suspend operator fun invoke(
        profileImageUri: String,
        profileImageName: String,
        nickname: String,
        gender: String,
        birth: LocalDate
    ): Result<Unit> {
        return getUrlAndUploadImageUseCase(
            imageUri = profileImageUri,
            fileName = profileImageName
        ).map { profileImageUrl ->
            editProfileUseCase(
                profileImageUrl = profileImageUrl,
                nickname = nickname,
                gender = gender,
                birth = birth
            ).getOrThrow()
        }
    }
}
