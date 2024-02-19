package ac.dnd.mour.android.domain.usecase.feature.group

import ac.dnd.mour.android.domain.repository.GroupRepository
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
