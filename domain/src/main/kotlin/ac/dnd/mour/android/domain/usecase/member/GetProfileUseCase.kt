package ac.dnd.mour.android.domain.usecase.member

import ac.dnd.mour.android.domain.model.member.Profile
import ac.dnd.mour.android.domain.repository.MemberRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Result<Profile> {
        return memberRepository.getProfile()
    }
}
