package com.example.uiassignmentseptember.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.uiassignmentseptember.Categorized
import com.example.uiassignmentseptember.Month
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.SwapScreenType
import com.example.uiassignmentseptember.generateRecord
import com.example.uiassignmentseptember.getImageIds
import com.example.uiassignmentseptember.model.CryptoActivity
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.toModel
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme

@Composable
fun Swap(
    images: List<Int>,
    modelId: Int,
    textFont: FontFamily,
    navController: NavController
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val size = screenWidthDp / 2
    val model = remember(modelId) {
        FakeDatabase().getModelFromID(modelId).toModel()
    }
    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    val standardPadding = 8.dp
    val splitedMoney = model.current.toString().split(".")

    var mode by rememberSaveable {
        mutableStateOf(SwapScreenType.ACTIVITY)
    }
    val record by rememberSaveable {
        mutableStateOf(generateRecord().map {
            Categorized(
                month = it.key.toString(),
                activities = it.value
            )
        })
    }

    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .padding(standardPadding),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            )
        ) {

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = model.imageId,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = model.name,
                    style = TextStyle(
                        color = secondaryColor
                    ),
                    fontSize = 15.sp,
                    fontFamily = textFont,
                    modifier = Modifier.padding(3.dp)
                )

                AsyncImage(
                    model = R.drawable.point_back,
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(270f)
                        .size(16.dp)
                )

                Spacer(modifier = Modifier.width(2.dp))
            }
        }

        Row {

            Spacer(modifier = Modifier.width(standardPadding))

            Text(
                text = "$${splitedMoney[0]}",
                style = TextStyle(
                    color = primaryColor
                ),
                fontFamily = textFont,
                fontSize = 30.sp
            )
            Text(
                text = ".${splitedMoney[1]}",
                style = TextStyle(
                    color = secondaryColor
                ),
                fontFamily = textFont,
                fontSize = 30.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(standardPadding))

            AsyncImage(
                model = if(model.positiveGrowth) R.drawable.up else R.drawable.down,
                contentDescription = null,
                modifier = Modifier.size(6.dp)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "$0.64 (${model.growthPercent}%)",
                style = TextStyle(
                    color = secondaryColor
                ),
                fontFamily = textFont,
                fontSize = 12.sp
            )
        }

        Row (
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LinkInGallery(
                imageId = R.drawable.share_plane,
                text = "Send",
                modifier = Modifier
                    .padding(standardPadding)
                    .width((size - 30).dp)
                    .height(50.dp)
            )

            LinkInGallery(
                imageId = R.drawable.share_plane,
                text = "Share",
                modifier = Modifier
                    .padding(standardPadding)
                    .width((size - 30).dp)
                    .height(50.dp)
            )
        }

        Row(

        ) {

            Spacer(modifier = Modifier.width(standardPadding))

            Text(
                text = "Tokens",
                style = TextStyle(
                    color = if (mode == SwapScreenType.TOKEN)
                        primaryColor
                    else secondaryColor
                ),
                fontFamily = textFont,
                fontSize = 15.sp,
                modifier = Modifier.clickable {
                    mode = SwapScreenType.TOKEN
                }
            )

            Spacer(modifier = Modifier.width(standardPadding))

            Text(
                text = "NFTs",
                style = TextStyle(
                    color = if (mode == SwapScreenType.NFTS)
                        primaryColor
                    else secondaryColor
                ),
                fontFamily = textFont,
                fontSize = 15.sp,
                modifier = Modifier.clickable {
                    mode = SwapScreenType.NFTS
                }
            )

            Spacer(modifier = Modifier.width(standardPadding))

            Text(
                text = "Activity",
                style = TextStyle(
                    color = if (mode == SwapScreenType.ACTIVITY)
                        primaryColor
                    else secondaryColor
                ),
                fontFamily = textFont,
                fontSize = 15.sp,
                modifier = Modifier.clickable {
                    mode = SwapScreenType.ACTIVITY
                }
            )
        }

        Spacer(modifier = Modifier.height(standardPadding))

        when(mode) {
            SwapScreenType.TOKEN -> {
            }

            SwapScreenType.NFTS -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(
                        images,
                        key = { it }
                    ) {
                        PhotoInGallery(
                            imageId = it,
                            size = size,
                            navController = navController,
                            modelId = modelId
                        )
                    }
                }
            }

            SwapScreenType.ACTIVITY ->
                Records(
                    category = record,
                    imagesIds = images
                )
        }
    }
}

@Stable
@Composable
fun PhotoInGallery(
    imageId:Int,
    size:Int,
    navController: NavController,
    modelId: Int
) {
    Card(
        modifier = Modifier
            .size(size.dp)
            .padding(5.dp)
    ) {
        val painter = rememberImagePainter(
            data = imageId,
            builder = {
                crossfade(500)
            }
        )

        val painterState = painter.state

        if (painterState == AsyncImagePainter.State.Loading(painter)) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.teal_200)
            )
        } else {
            Image(
                painter = painter,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.navigate("PhotoDetail/$modelId/$imageId")
                    },
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
    }
}
@Composable
fun LinkInGallery(
    imageId: Int,
    text: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .padding(2.dp)
            )

            Text(
                text = text,
                style = TextStyle(
                    color = Color.White
                ),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.impact)),
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}

@Composable
fun MonthHeader(text: String) {
    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White
            ),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.impact)),
            modifier = Modifier.padding(start = 12.dp)
        )
    }

}

@Composable
fun ActivityUI(
    activity: CryptoActivity,
    imageId: Int
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val topFontSize = 16.sp
    val bottomFontSize = 14.sp
    val activityTime = if (activity.time.date.month == Month.Today) {
        activity.time.hour.text
    } else {
        "${activity.time.date.month} ${activity.time.date.day}"
    }

    ConstraintLayout(
        modifier = Modifier
            .height(75.dp)
            .background(color = Color.Black)
            .fillMaxWidth()
    ) {
        val (image, type, time, detail) = createRefs()

        AsyncImage(
            model = imageId,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .padding(5.dp)
                .clip(CircleShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = activity.type,
            fontSize = topFontSize,
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            modifier = Modifier.constrainAs(type) {
                start.linkTo(image.end)
                top.linkTo(parent.top, margin = 5.dp)
                bottom.linkTo(detail.top)
            }
        )

        Text(
            text = activityTime,
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            fontSize = topFontSize,
            modifier = Modifier.constrainAs(time) {
                top.linkTo(parent.top)
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(parent.bottom)
            }
        )

        Text(
            text = activity.detail,
            fontSize = bottomFontSize,
            style = TextStyle(
                color = secondaryTextColor
            ),
            fontFamily = textFont,
            modifier = Modifier.constrainAs(detail) {
                start.linkTo(image.end)
                bottom.linkTo(parent.bottom, margin = 5.dp)
                top.linkTo(type.bottom)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Records(
    category: List<Categorized>,
    imagesIds: List<Int>
) {
    LazyColumn {
        category.forEach {
            stickyHeader {
                MonthHeader(text = it.month)
            }

            val sortedActivities: List<CryptoActivity> = if(it.month == "Today") {
                it.activities.sortedByDescending { it.time.hour.value }
            } else {
                it.activities.sortedByDescending { it.time.date.day }
            }


            items(
                sortedActivities,
                key = { it.id }
            ) { activity ->
                val imageId by rememberSaveable {
                    mutableIntStateOf(imagesIds.random())
                }
                ActivityUI(
                    activity = activity,
                    imageId = imageId
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGallery() {
    UiAssignmentSeptemberTheme {
        Swap(
            images = getImageIds(LocalContext.current),
            modelId = 3,
            textFont = FontFamily(Font(R.font.impact)),
            navController = NavController(LocalContext.current)
        )
    }
}