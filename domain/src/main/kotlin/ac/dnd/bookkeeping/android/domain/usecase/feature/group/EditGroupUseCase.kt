package ac.dnd.bookkeeping.android.domain.usecase.feature.group

import ac.dnd.bookkeeping.android.domain.repository.GroupRepository
import javax.inject.Inject

class EditGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        id: Long,
        name: String
    ): Result<Unit> {
        return groupRepository.editGroup(
            id = id,
            name = name
        )
    }
}
