package ac.dnd.mour.android.common

fun Char?.orEmpty(): Char {
    return this ?: Char.MIN_VALUE
}
