package ac.dnd.bookkeeping.android.domain.usecase.feature.group

import ac.dnd.bookkeeping.android.domain.repository.GroupRepository
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        name: String
    ): Result<Long> {
        return groupRepository.addGroup(
            name = name
        )
    }
}
