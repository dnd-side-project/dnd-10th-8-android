package ac.dnd.mour.android.presentation.model.history

import ac.dnd.mour.android.presentation.R

enum class HistoryDetailGrowthType(
    val typeName: String,
    val price: Long,
    val iconImageResource: Int,
    val backgroundColor: Long,
    val level: Int
) {
    LEVEL_ONE(
        typeName = "어색한 사이",
        price = 0,
        iconImageResource = R.drawable.ic_property_lv_1,
        backgroundColor = 0xFFA5A5A5,
        level = 1
    ),
    LEVEL_TWO(
        typeName = "알아가는 사이",
        price = 30_000,
        iconImageResource = R.drawable.ic_property_lv_2,
        backgroundColor = 0xFF67A3FC,
        level = 2
    ),
    LEVEL_THREE(
        typeName = "꽤 가까운 사이",
        price = 60_000,
        iconImageResource = R.drawable.ic_property_lv_3,
        backgroundColor = 0xFFE690E7,
        level = 3
    ),
    LEVEL_FOUR(
        typeName = "친한 사이",
        price = 100_000,
        iconImageResource = R.drawable.ic_property_lv_4,
        backgroundColor = 0xFF8B89FF,
        level = 4
    ),
    LEVEL_FIVE(
        typeName = "믿음직한 사이",
        price = 500_000,
        iconImageResource = R.drawable.ic_property_lv_5,
        backgroundColor = 0xFF594ED0,
        level = 5
    ),
    LEVEL_SIX(
        typeName = "평생을 함께 할 사이",
        price = 1_000_000,
        iconImageResource = R.drawable.ic_property_lv_6,
        backgroundColor = 0xFFE788FF,
        level = 6
    );

    companion object {
        fun getGrowthType(
            totalMoney: Long
        ): HistoryDetailGrowthType {
            return when (totalMoney) {
                in LEVEL_ONE.price until LEVEL_TWO.price -> LEVEL_ONE
                in LEVEL_TWO.price until LEVEL_THREE.price -> LEVEL_TWO
                in LEVEL_THREE.price until LEVEL_FOUR.price -> LEVEL_THREE
                in LEVEL_FOUR.price until LEVEL_FIVE.price -> LEVEL_FOUR
                in LEVEL_FIVE.price until LEVEL_SIX.price -> LEVEL_FIVE
                else -> LEVEL_SIX
            }
        }

        fun getTypeString(
            type: HistoryDetailGrowthType
        ): String {
            return when (type) {
                LEVEL_ONE -> "(~${LEVEL_TWO.price / 10000}만원 미만)"
                LEVEL_TWO -> "(${LEVEL_TWO.price / 10000}만원 이상 ~ ${LEVEL_THREE.price / 10000}만원 미만)"
                LEVEL_THREE -> "(${LEVEL_THREE.price / 10000}만원 이상 ~ ${LEVEL_FOUR.price / 10000}만원 미만)"
                LEVEL_FOUR -> "(${LEVEL_FOUR.price / 10000}만원 이상 ~ ${LEVEL_FIVE.price / 10000}만원 미만)"
                LEVEL_FIVE -> "(${LEVEL_FIVE.price / 10000}만원 이상 ~ ${LEVEL_SIX.price / 10000}만원 미만)"
                LEVEL_SIX -> "(${LEVEL_SIX.price / 10000}만원 이상 ~)"
            }
        }
    }
}
