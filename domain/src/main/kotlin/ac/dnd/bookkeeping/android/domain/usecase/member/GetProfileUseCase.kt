package ac.dnd.bookkeeping.android.domain.usecase.member

import ac.dnd.bookkeeping.android.domain.model.member.Profile
import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Result<Profile> {
        return memberRepository.getProfile()
    }
}
