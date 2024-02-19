package ac.dnd.mour.android.presentation.common.view.textfield

sealed interface TypingTextFieldType {
    data object Basic : TypingTextFieldType
    data object LongSentence : TypingTextFieldType
}
