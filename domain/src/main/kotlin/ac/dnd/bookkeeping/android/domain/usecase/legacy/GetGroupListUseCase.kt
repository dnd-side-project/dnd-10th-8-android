package ac.dnd.bookkeeping.android.domain.usecase.legacy

import ac.dnd.bookkeeping.android.domain.model.legacy.GroupLegacy
import ac.dnd.bookkeeping.android.domain.model.legacy.RelationLegacy
import ac.dnd.bookkeeping.android.domain.model.legacy.RelationGroupLegacy
import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import javax.inject.Inject

class GetGroupListUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Result<List<GroupLegacy>> {
        // TODO
        return Result.success(
            listOf(
                GroupLegacy(
                    id = 2337,
                    name = "Angelia McBride",
                    relations = listOf(
                        RelationLegacy(
                            id = 9922,
                            name = "Patty Meadows",
                            group = RelationGroupLegacy(
                                id = 2337,
                                name = "Angelia McBride",
                            ),
                            giveMoney = 8246,
                            takeMoney = 5441
                        ),
                        RelationLegacy(
                            id = 1447,
                            name = "Margery Hyde",
                            group = RelationGroupLegacy(
                                id = 2337,
                                name = "Angelia McBride",
                            ),
                            giveMoney = 2859,
                            takeMoney = 1341
                        ),
                        RelationLegacy(
                            id = 8446,
                            name = "Harlan Yang",
                            group = RelationGroupLegacy(
                                id = 2337,
                                name = "Angelia McBride",
                            ),
                            giveMoney = 8327,
                            takeMoney = 4954
                        )
                    )
                ),
                GroupLegacy(
                    id = 6599,
                    name = "Andrea Serrano",
                    relations = listOf(
                        RelationLegacy(
                            id = 3679,
                            name = "Jerome Pitts",
                            group = RelationGroupLegacy(
                                id = 6599,
                                name = "Andrea Serrano",
                            ),
                            giveMoney = 4190,
                            takeMoney = 4010
                        )
                    )
                )
            )
        )
    }
}
