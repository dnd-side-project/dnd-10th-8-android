package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.common

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryDetailGrowthType
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HistoryBackgroundComponent(
    currentGrowthType: HistoryDetailGrowthType,
    backgroundHeight: Dp,
    currentScreenHeightRatio: Float,
    isInformationShowing: Boolean = true,
    innerContent: @Composable () -> Unit = {},
    onClickInformation: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(backgroundHeight)
    ) {
        Image(
            painter = painterResource(currentGrowthType.backgroundImageResource),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        LocalConfiguration.current.screenHeightDp.dp
        innerContent()

        //TODO 로티
        if (isInformationShowing) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = 20.dp,
                        top = 389.dp * currentScreenHeightRatio
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onClickInformation()
                    }
                )
            }
        }
    }
}
