package ac.dnd.bookkeeping.android.presentation.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Primary2,
    primaryVariant = Primary4,
    secondary = Secondary1,
    background = Gray600,
    surface = Gray700,
    onPrimary = Gray800,
    onSecondary = Gray800,
    onBackground = Gray000,
    onSurface = Gray000,
)

private val LightColorPalette = lightColors(
    primary = Primary4,
    primaryVariant = Primary1,
    secondary = Secondary2,
    background = Gray200,
    surface = Gray100,
    onPrimary = Gray000,
    onSecondary = Gray000,
    onBackground = Gray800,
    onSurface = Gray800,
)

@Composable
fun BookkeepingTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(colors.background)
        systemUiController.isNavigationBarVisible = false
    }
}
