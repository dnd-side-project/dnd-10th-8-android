package ac.dnd.bookkeeping.android.common

fun Char?.orEmpty(): Char {
    return this ?: Char.MIN_VALUE
}
