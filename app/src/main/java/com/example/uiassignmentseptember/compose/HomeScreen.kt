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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.Model
import com.example.uiassignmentseptember.model.toModel
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme

@Composable
fun HomeScreen(
    navController: NavController
) {
    val dataList = FakeDatabase().getFakeData()

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(1000.dp)
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,) {
            LazyColumn {
                items(dataList,key = { it.id } ) {
                    ConstraintSelector(
                        model = it,
                        navController = navController
                    )
                }
            }
        }
    }
}
/*
@Composable
fun Selector(
    model: Model,
    navController: NavController
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val topFontSize = 16.sp
    val bottomFontSize = 14.sp

    Surface(
        modifier = Modifier
            .clickable {
                navController.navigate("infoScreen/${model.id}")
            }
            .height(75.dp)
            .fillMaxWidth()
            .background(Color.Black),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = model.imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
                    .clip(CircleShape)
            )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(
                        text = model.name,
                        fontSize = topFontSize,
                        style = TextStyle(
                            color = primaryTextColor
                        ),
                        fontFamily = textFont
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = model.description,
                    fontSize = bottomFontSize,
                    style = TextStyle(
                        color = secondaryTextColor
                    ),
                    fontFamily = textFont
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(end = 3.dp)
                    ) {
                        Text(
                            text = "$${model.current}",
                            textAlign = TextAlign.End,
                            style = TextStyle(
                                color = primaryTextColor
                            ),
                            fontFamily = textFont,
                            fontSize = topFontSize
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(
                                    id = if(model.positiveGrowth) R.drawable.up else R.drawable.down),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(8.dp),
                                contentScale = ContentScale.FillBounds
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "${model.growthPercent}%",
                                style = TextStyle(
                                    color = secondaryTextColor
                                ),
                                fontFamily = textFont,
                                fontSize = bottomFontSize
                            )
                        }
                    }
                }
            }
        }
    }
}*/

@Composable
fun ConstraintSelector(
    model: Model,
    navController: NavController
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val topFontSize = 16.sp
    val bottomFontSize = 14.sp

    ConstraintLayout(
        modifier = Modifier
            .height(75.dp)
            .background(color = Color.Black)
            .fillMaxWidth()
            .clickable { navController.navigate("infoScreen/${model.id}") }
    ) {
        val (image, name,money, growth, description, percent) = createRefs()

        Image(
            painter = painterResource(id = model.imageId),
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
            text = model.name,
            fontSize = topFontSize,
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(image.end)
                top.linkTo(parent.top, margin = 5.dp)
                bottom.linkTo(description.top)
            }
        )

        Text(
            text = "$${model.current}",
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            fontSize = topFontSize,
            modifier = Modifier.constrainAs(money) {
                top.linkTo(parent.top, margin = 5.dp)
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(percent.top)
            }
        )

        Text(
            text = model.description,
            fontSize = bottomFontSize,
            style = TextStyle(
                color = secondaryTextColor
            ),
            fontFamily = textFont,
            modifier = Modifier.constrainAs(description) {
                start.linkTo(image.end)
                bottom.linkTo(parent.bottom, margin = 5.dp)
                top.linkTo(name.bottom)
            }
        )

        Image(
            painter = painterResource(
                id = if(model.positiveGrowth) R.drawable.up else R.drawable.down),
            contentDescription = null,
            modifier = Modifier
                .size(8.dp)
                .constrainAs(growth) {
                    end.linkTo(percent.start, margin = 5.dp)
                    top.linkTo(percent.top)
                    bottom.linkTo(percent.bottom)
                },
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "${model.growthPercent}%",
            style = TextStyle(
                color = secondaryTextColor
            ),
            fontFamily = textFont,
            fontSize = bottomFontSize,
            modifier = Modifier.constrainAs(percent) {
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 5.dp)
                top.linkTo(money.bottom)
            }
        )
    }
}
/*@Preview(showBackground = true)
@Composable
fun PreviewSelection() {
    UiAssignmentSeptemberTheme {
        Column {
            Selector(
                model = FakeDatabase().getModelFromID(2).toModel(),
                navController = NavController(LocalContext.current)
            )

            Selector(
                model = FakeDatabase().getModelFromID(3).toModel(),
                navController = NavController(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            val nav = NavController(LocalContext.current)
            ConstraintSelector(model = FakeDatabase().getModelFromID(1).toModel(),nav)
            ConstraintSelector(model = FakeDatabase().getModelFromID(2).toModel(),nav)
        }
    }
}*/

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        HomeScreen(navController = NavController(LocalContext.current))
    }
}