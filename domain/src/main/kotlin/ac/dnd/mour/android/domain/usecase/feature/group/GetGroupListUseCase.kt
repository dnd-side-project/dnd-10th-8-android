package ac.dnd.mour.android.domain.usecase.feature.group

import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupListUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(): Result<List<Group>> {
        return groupRepository.getGroupList()
    }
}
