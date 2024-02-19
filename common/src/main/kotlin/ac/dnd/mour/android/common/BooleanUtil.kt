package ac.dnd.mour.android.common

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}

fun Boolean?.orTrue(): Boolean {
    return this ?: true
}
