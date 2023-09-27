package com.example.uiassignmentseptember.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.getRandomName
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.toModel
import kotlin.random.Random

@Composable
fun PhotoDetail(
    modelId:Int,
    photoId:Int,
    navController:NavController
) {
    val model by remember {
        mutableStateOf(FakeDatabase().getModelFromID(modelId).toModel())
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val name by rememberSaveable {
        mutableStateOf(getRandomName())
    }

    val enemyName by rememberSaveable {
        mutableStateOf(getRandomName())
    }

    val tag by rememberSaveable {
        mutableStateOf("#${Random.nextInt(0,9999)}")
    }

    val sideScreenPadding = 12.dp

    Scaffold(
        topBar = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.Black)
            ) {
                val (threeDots,arrow) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.point_back),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { navController.navigate("InfoScreen/${model.id}") }
                        .size(20.dp)
                        .constrainAs(arrow) {
                            start.linkTo(parent.start, margin = 15.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.three_dots),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .constrainAs(threeDots) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, margin = 15.dp)
                        }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.Black)
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = photoId,
                    contentDescription = null,
                    modifier = Modifier
                        .size(screenWidth.dp)
                        .padding(
                            start = sideScreenPadding,
                            end = sideScreenPadding
                        )
                )
            }

            Text(
                text = "$name $tag",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.impact)),
                style = TextStyle(
                    color = colorResource(id = R.color.teal_200)
                ),
                modifier = Modifier
                    .padding(start = sideScreenPadding)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = Color.DarkGray)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.width(3.dp))

                    Image(
                        painter = painterResource(id = R.drawable.token_unity),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )

                    Column() {
                        Text(
                            text = name,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.impact)),
                            style = TextStyle(
                                color = colorResource(id = R.color.teal_200)
                            ),
                            modifier = Modifier
                                .padding(start = sideScreenPadding)
                        )

                        Row() {
                            Text(
                                text = "Floor:",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.impact)),
                                style = TextStyle(
                                    color = colorResource(id = R.color.teal_700)
                                ),
                                modifier = Modifier
                                    .padding(start = sideScreenPadding)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.crystal_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                            )

                            Text(
                                text = "0.420",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.impact)),
                                style = TextStyle(
                                    color = colorResource(id = R.color.teal_700)
                                ),
                                modifier = Modifier
                                    .padding(start = sideScreenPadding)
                            )
                        }
                    }
                }
            }

            Text(
                text = "Arch-Nemesis of $enemyName their battle shook metaverse to it's core",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.impact)),
                style = TextStyle(
                    color = colorResource(id = R.color.teal_200)
                ),
                modifier = Modifier
                    .padding(start = sideScreenPadding)
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoDetail() {
    PhotoDetail(modelId = 4, photoId = R.drawable.baraka_obama, navController = NavController(LocalContext.current))
}