package com.dnd_9th_3_android.gooding.record.tabGallery

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.common.view.gallery.GalleryIntent
import ac.dnd.bookkeeping.android.presentation.common.view.gallery.GalleryModel
import ac.dnd.bookkeeping.android.presentation.common.view.gallery.GalleryViewModel
import ac.dnd.bookkeeping.android.presentation.common.view.gallery.item.GalleryItemContent
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import android.Manifest
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.coroutine.TedPermission

@Composable
fun GalleryScreen(
    appState: ApplicationState,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val model: GalleryModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val images by viewModel.galleryPhotoList.collectAsStateWithLifecycle()
        val folders by viewModel.folders.collectAsStateWithLifecycle()
        val currentFolder by viewModel.currentFolder.collectAsStateWithLifecycle()
        GalleryModel(
            state = state,
            folders = folders,
            currentFolder = currentFolder,
            galleryImages = images
        )
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isCheckPermission by remember { mutableStateOf(false) }
    var currentSelectedId by remember { mutableLongStateOf(-1) }
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var isPreventSelectMessage by remember { mutableStateOf(false) }
    SideEffect {
        requestPermission(
            logic = {
                isCheckPermission = true
            }
        )
    }

    if (isCheckPermission) {
        viewModel.onIntent(GalleryIntent.OnGrantPermission)
    }

    Column(modifier = Modifier.background(Gray000)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        appState.navController.popBackStack()
                    }
                    .align(Alignment.CenterStart)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
                    .clickable {
                        isDropDownMenuExpanded = !isDropDownMenuExpanded
                    }
            ) {
                Text(
                    text = model.currentFolder.first,
                    style = Headline1.merge(
                        color = Gray800,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(Space4))
                Image(
                    painter = painterResource(id = R.drawable.ic_chevron_down),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(if (isDropDownMenuExpanded) 180f else 0f)
                )
            }

            if (!isDropDownMenuExpanded) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterEnd)
                        .clickable {
                            // 클릭 가능 상태면 이동, 다음 텍스트 변경
                            if (currentSelectedId > 0) {
                                // TODO next
                            }
                        }
                ) {
                    Text(
                        text = "확인",
                        style = Headline3.merge(
                            color = if (currentSelectedId > 0) Primary4 else Gray600,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

        }

        Box(Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(3),
            ) {
                items(model.galleryImages) { image ->
                    GalleryItemContent(
                        galleryImage = image,
                        currentSelectedImageId = currentSelectedId,
                        onSelectImage = {
                            currentSelectedId = it.id
                        },
                        onDeleteImage = {
                            currentSelectedId = -1
                        }
                    )
                }
            }

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                if (isDropDownMenuExpanded) {
                    DropdownMenu(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(Gray300),
                        expanded = isDropDownMenuExpanded,
                        onDismissRequest = { isDropDownMenuExpanded = false },
                    ) {
                        model.folders.map { folder ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.setCurrentFolder(folder)
                                    isDropDownMenuExpanded = false
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = folder.first,
                                        style = Headline3
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun requestPermission(
    logic: () -> Unit
) {
    TedPermission.create()
        .setPermissionListener(object : PermissionListener {
            override fun onPermissionGranted() {
                logic()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                TODO("Not yet implemented")
            }

        })
        .setDeniedMessage("이미지 접근 권한을 허용해주세요.")
        .setPermissions(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        )
}
