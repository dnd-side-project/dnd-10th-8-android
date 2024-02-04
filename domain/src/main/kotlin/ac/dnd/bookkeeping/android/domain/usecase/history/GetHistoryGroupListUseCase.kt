package ac.dnd.bookkeeping.android.domain.usecase.history

import ac.dnd.bookkeeping.android.domain.model.legacy.GroupLegacy
import ac.dnd.bookkeeping.android.domain.model.legacy.RelationLegacy
import ac.dnd.bookkeeping.android.domain.model.legacy.RelationGroupLegacy
import javax.inject.Inject

class GetHistoryGroupListUseCase @Inject constructor(

) {
    suspend operator fun invoke(
        historyType: String
    ): Result<List<GroupLegacy>> {
        return Result.success(
            when (historyType) {
                "take" -> {
                    listOf(
                        GroupLegacy(
                            1,
                            "전체-받은 마음",
                            listOf(
                                RelationLegacy(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroupLegacy(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 1000000
                                ),
                                RelationLegacy(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroupLegacy(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroupLegacy(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 23
                                ),
                                RelationLegacy(
                                    id = 1,
                                    name = "박예리나",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 0,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 2,
                                    name = "이다빈",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 12312223,
                                    takeMoney = 12
                                ),
                                RelationLegacy(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroupLegacy(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 4345346
                                ),
                            )
                        ),
                        GroupLegacy(
                            2,
                            "친구",
                            listOf(
                                RelationLegacy(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroupLegacy(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 0
                                ),
                            )
                        ),
                        GroupLegacy(
                            3,
                            "가족",
                            listOf(
                                RelationLegacy(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroupLegacy(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 1000000,
                                    takeMoney = 23
                                ),
                            )
                        ),
                        GroupLegacy(
                            4,
                            "지인",
                            listOf(
                                RelationLegacy(
                                    id = 1,
                                    name = "박예리나",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 0,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 2,
                                    name = "이다빈",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 12231223,
                                    takeMoney = 12
                                ),
                            )
                        ),
                        GroupLegacy(
                            5,
                            "직장",
                            listOf(
                                RelationLegacy(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroupLegacy(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 4345346
                                ),
                            )
                        ),
                        GroupLegacy(
                            6,
                            "사촌",
                            listOf(
                                RelationLegacy(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroupLegacy(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 1000000
                                ),
                            )
                        )
                    )
                }

                "give" -> {
                    listOf(
                        GroupLegacy(
                            1,
                            "전체-준마음",
                            listOf(
                                RelationLegacy(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroupLegacy(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 1000000
                                ),
                                RelationLegacy(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroupLegacy(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroupLegacy(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 23
                                ),
                                RelationLegacy(
                                    id = 1,
                                    name = "박예리나",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 0,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 2,
                                    name = "이다빈",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 12231223,
                                    takeMoney = 12
                                ),
                                RelationLegacy(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroupLegacy(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 0
                                ),
                            )
                        ),
                        GroupLegacy(
                            2,
                            "친구",
                            listOf(
                                RelationLegacy(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroupLegacy(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 10000,
                                    takeMoney = 0
                                ),
                            )
                        ),
                        GroupLegacy(
                            3,
                            "가족",
                            listOf(
                                RelationLegacy(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroupLegacy(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 23
                                ),
                            )
                        ),
                        GroupLegacy(
                            4,
                            "지인",
                            listOf(
                                RelationLegacy(
                                    id = 1,
                                    name = "박예리나",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 0,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 2,
                                    name = "이다빈",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 1231223,
                                    takeMoney = 12
                                ),
                            )
                        ),
                        GroupLegacy(
                            5,
                            "직장",
                            listOf(
                                RelationLegacy(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroupLegacy(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 4345346
                                ),
                            )
                        ),
                        GroupLegacy(
                            6,
                            "사촌",
                            listOf(
                                RelationLegacy(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroupLegacy(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 10000000
                                ),
                            )
                        )
                    )
                }

                else -> {
                    listOf(
                        GroupLegacy(
                            1,
                            "전체-전체",
                            listOf(
                                RelationLegacy(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroupLegacy(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 10000000
                                ),
                                RelationLegacy(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroupLegacy(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 1000000,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroupLegacy(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 10000000,
                                    takeMoney = 23
                                ),
                                RelationLegacy(
                                    id = 1,
                                    name = "박예리나",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 0,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 2,
                                    name = "이다빈",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 123121223,
                                    takeMoney = 12
                                ),
                                RelationLegacy(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroupLegacy(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 43534346
                                ),
                            )
                        ),
                        GroupLegacy(
                            2,
                            "친구",
                            listOf(
                                RelationLegacy(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroupLegacy(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 1000000,
                                    takeMoney = 0
                                ),
                            )
                        ),
                        GroupLegacy(
                            3,
                            "가족",
                            listOf(
                                RelationLegacy(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroupLegacy(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 10000000,
                                    takeMoney = 23
                                ),
                            )
                        ),
                        GroupLegacy(
                            4,
                            "지인",
                            listOf(
                                RelationLegacy(
                                    id = 1,
                                    name = "박예리나",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 0,
                                    takeMoney = 0
                                ),
                                RelationLegacy(
                                    id = 2,
                                    name = "이다빈",
                                    group = RelationGroupLegacy(
                                        id = 1,
                                        name = "지인",
                                    ),
                                    giveMoney = 1231223,
                                    takeMoney = 12
                                ),
                            )
                        ),
                        GroupLegacy(
                            5,
                            "직장",
                            listOf(
                                RelationLegacy(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroupLegacy(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 43534546
                                ),
                            )
                        ),
                        GroupLegacy(
                            6,
                            "사촌",
                            listOf(
                                RelationLegacy(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroupLegacy(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 1000000
                                ),
                            )
                        )
                    )
                }
            }
        )
    }
}
