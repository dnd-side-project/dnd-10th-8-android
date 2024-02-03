package ac.dnd.bookkeeping.android.domain.usecase.feature.member

import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import javax.inject.Inject

class CheckNicknameUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(
        nickname: String
    ): Result<Boolean> {
        return memberRepository.checkNickname(
            nickname = nickname
        )
    }
}
