package ac.dnd.mour.android.domain.usecase.member

import ac.dnd.mour.android.domain.repository.MemberRepository
import javax.inject.Inject
import kotlinx.datetime.LocalDate

class EditProfileUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(
        profileImageUrl: String,
        nickname: String,
        gender: String,
        birth: LocalDate
    ): Result<Unit> {
        return memberRepository.editProfile(
            profileImageUrl = profileImageUrl,
            nickname = nickname,
            gender = gender,
            birth = birth
        )
    }
}
