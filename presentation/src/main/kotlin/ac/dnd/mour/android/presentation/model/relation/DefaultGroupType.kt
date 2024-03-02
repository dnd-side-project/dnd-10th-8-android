package ac.dnd.mour.android.presentation.model.relation

import ac.dnd.mour.android.presentation.R

enum class DefaultGroupType(
    private val typeName: String,
    private val iconResource: Int
) {
    FRIEND("친구", R.drawable.ic_group_friend),
    FAMILY("가족", R.drawable.ic_group_family),
    ACQUAINTANCE("지인", R.drawable.ic_group_acquaintance),
    RECTAL("직장", R.drawable.ic_group_rectal);

    fun getTypeName() = typeName

    companion object {
        fun checkEditable(name: String): Boolean {
            return entries.find { it.typeName == name } == null
        }

        fun getGroupResource(name: String): Int {
            return entries.find { it.typeName == name }?.iconResource
                ?: R.drawable.ic_group_normal
        }
    }
}
