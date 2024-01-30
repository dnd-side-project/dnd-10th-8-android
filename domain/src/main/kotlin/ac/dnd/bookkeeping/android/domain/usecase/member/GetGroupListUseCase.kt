package ac.dnd.bookkeeping.android.domain.usecase.member

import ac.dnd.bookkeeping.android.domain.model.event.Group
import ac.dnd.bookkeeping.android.domain.model.event.Relation
import ac.dnd.bookkeeping.android.domain.model.event.RelationGroup
import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import javax.inject.Inject

class GetGroupListUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Result<List<Group>> {
        // TODO
        return Result.success(
            listOf(
                Group(
                    id = 2337,
                    name = "Angelia McBride",
                    relations = listOf(
                        Relation(
                            id = 9922,
                            name = "Patty Meadows",
                            group = RelationGroup(
                                id = 2337,
                                name = "Angelia McBride",
                            ),
                            giveMoney = 8246,
                            takeMoney = 5441
                        ),
                        Relation(
                            id = 1447,
                            name = "Margery Hyde",
                            group = RelationGroup(
                                id = 2337,
                                name = "Angelia McBride",
                            ),
                            giveMoney = 2859,
                            takeMoney = 1341
                        ),
                        Relation(
                            id = 8446,
                            name = "Harlan Yang",
                            group = RelationGroup(
                                id = 2337,
                                name = "Angelia McBride",
                            ),
                            giveMoney = 8327,
                            takeMoney = 4954
                        )
                    )
                ),
                Group(
                    id = 6599,
                    name = "Andrea Serrano",
                    relations = listOf(
                        Relation(
                            id = 3679,
                            name = "Jerome Pitts",
                            group = RelationGroup(
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
