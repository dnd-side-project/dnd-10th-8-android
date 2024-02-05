package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery

sealed interface GalleryIntent {
    data object OnGrantPermission : GalleryIntent
    data class OnChangeFolder(
        val location: Pair<String, String?>
    ) : GalleryIntent
}
