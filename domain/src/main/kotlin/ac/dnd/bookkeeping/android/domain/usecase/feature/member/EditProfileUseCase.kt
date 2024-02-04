package ac.dnd.bookkeeping.android.domain.usecase.feature.member

import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

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
