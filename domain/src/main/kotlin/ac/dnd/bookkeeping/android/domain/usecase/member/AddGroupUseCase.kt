package ac.dnd.bookkeeping.android.domain.usecase.member

import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(
        name: String
    ): Result<Unit> {
        // TODO
        return Result.success(Unit)
    }
}
