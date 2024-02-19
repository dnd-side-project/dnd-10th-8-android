package ac.dnd.mour.android.domain.model.legacy

data class RelationLegacy(
    val id: Long,
    val name: String,
    val group: RelationGroupLegacy,
    val giveMoney: Long,
    val takeMoney: Long
)

data class RelationGroupLegacy(
    val id: Long,
    val name: String
)
