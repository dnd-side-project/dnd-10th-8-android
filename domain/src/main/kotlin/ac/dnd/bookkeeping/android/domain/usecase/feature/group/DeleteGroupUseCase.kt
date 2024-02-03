package ac.dnd.bookkeeping.android.domain.usecase.feature.group

import ac.dnd.bookkeeping.android.domain.repository.GroupRepository
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        id: Long,
    ): Result<Unit> {
        return groupRepository.deleteGroup(
            id = id
        )
    }
}
