package ac.dnd.bookkeeping.android.presentation.ui.main.home

import ac.dnd.bookkeeping.android.presentation.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeBottomBarScreen(
    selectedItem: Int,
    onClick: (Int) -> Unit
) {
    BottomNavigation(
        elevation = 0.dp,
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        val list = listOf("bookkeeping", "setting")
        list.forEachIndexed { index, screen ->
            BottomNavigationItem(
                selected = index == selectedItem,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.bottom_sample),
                        contentDescription = "bottom icon"
                    )
                },
                label = {
                    Text(
                        text = screen,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(0.dp)
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                onClick = {
                    onClick(index)
                }
            )
        }
    }
}
