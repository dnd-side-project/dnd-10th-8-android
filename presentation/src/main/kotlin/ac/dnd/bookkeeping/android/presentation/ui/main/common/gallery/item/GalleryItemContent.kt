package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery.item

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray150
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.dnd_9th_3_android.gooding.model.record.GalleryImage

@Composable
fun GalleryItemContent(
    galleryImage: GalleryImage,
    currentSelectedImageId: Long,
    onSelectImage: (GalleryImage) -> Unit,
    onDeleteImage: () -> Unit
) {
    val selected = currentSelectedImageId == galleryImage.id
    Box {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(galleryImage.uri)
                .crossfade(true)
                .build(),
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Gray150),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Primary1,
                        modifier = Modifier.fillMaxSize(0.5f)
                    )

                }
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .animateContentSize()
                .clickable {
                    if (selected) {
                        onDeleteImage()
                    } else {
                        onSelectImage(galleryImage)
                    }
                },
            alpha = if (selected || currentSelectedImageId==-1L ) 1f else 0.4f
        )
        if (selected) {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_check_circle),
                    contentDescription = null
                )
            }
        }
    }
}
