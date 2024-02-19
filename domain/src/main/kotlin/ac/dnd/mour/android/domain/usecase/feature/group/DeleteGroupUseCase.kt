package ac.dnd.mour.android.domain.usecase.feature.group

import ac.dnd.mour.android.domain.repository.GroupRepository
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
