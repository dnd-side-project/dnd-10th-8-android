package ac.dnd.mour.android.presentation.common.theme

import ac.dnd.mour.android.presentation.R
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val EmptyTextStyle = TextStyle(
    fontSize = 0.sp,
    fontFamily = FontFamily(Font(R.font.pretendard))
)

val Typography = Typography(
    defaultFontFamily = FontFamily(Font(R.font.pretendard)),
    h1 = EmptyTextStyle,
    h2 = EmptyTextStyle,
    h3 = EmptyTextStyle,
    h4 = EmptyTextStyle,
    h5 = EmptyTextStyle,
    h6 = EmptyTextStyle,
    subtitle1 = EmptyTextStyle,
    subtitle2 = EmptyTextStyle,
    body1 = EmptyTextStyle,
    body2 = EmptyTextStyle,
    button = EmptyTextStyle,
    caption = EmptyTextStyle,
    overline = EmptyTextStyle,
)

val Headline0 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp
)
val Headline1 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp
)
val Headline2 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp
)
val Headline3 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
)
val Body0 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)
val Body1 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp
)
val Body2 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
val Caption1 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
val Caption2 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp
)
val Caption3 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp
)
val Caption4 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 10.sp
)
val Caption5 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp
)
