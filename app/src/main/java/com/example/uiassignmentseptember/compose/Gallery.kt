package com.example.uiassignmentseptember.compose

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.getImageIds
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.toModel
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme

@Composable
fun Gallery(
    images: List<Int>,
    modelId: Int,
    textFont: FontFamily,
    navController: NavController
) {
    val size = LocalConfiguration.current.screenWidthDp / 2
    val model by remember {
        mutableStateOf(FakeDatabase().getModelFromID(modelId).toModel())
    }
    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    val standardPadding by remember {
        mutableStateOf(8.dp)
    }
    val splitedMoney = model.current.toString().split(".")

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
            var thisFontSize by rememberSaveable {
                mutableIntStateOf(15)
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = model.imageId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = " ${model.name}",
                    style = TextStyle(
                        color = secondaryColor
                    ),
                    fontSize = thisFontSize.sp,
                    fontFamily = textFont,
                    modifier = Modifier.padding(3.dp),
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.didOverflowWidth) {
                                thisFontSize -= 1
                        }
                    }
                )

                Image(
                    painter = painterResource(id = R.drawable.point_back),
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

            Image(
                painter = painterResource(
                    id = if(model.positiveGrowth)
                        R.drawable.up
                    else R.drawable.down
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(6.dp)
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
                    navController = navController
                )
            }
        }
    }
}

@Stable
@Composable
fun PhotoInGallery(
    imageId:Int,
    size:Int,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .size(size.dp)
            .padding(5.dp)
    ) {
        val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = imageId).apply(block = fun ImageRequest.Builder.() {
                    crossfade(500)
                }
            ).build()
        )

        val painterState = painter.state

        if (painterState == AsyncImagePainter.State.Loading(painter)) {
            CircularProgressIndicator()
        } else {
            Image(
                painter = painter,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.navigate("PhotoDetail/$imageId")
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
            Image(
                painter = painterResource(id = imageId),
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
@Preview(showBackground = true)
@Composable
fun PreviewGallery() {
    UiAssignmentSeptemberTheme {
        Gallery(
            images = getImageIds(LocalContext.current),
            modelId = 3,
            textFont = FontFamily(Font(R.font.impact)),
            navController = NavController(LocalContext.current)
        )
    }
}