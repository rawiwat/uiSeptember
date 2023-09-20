package com.example.uiassignmentseptember.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    val dataList by rememberSaveable {
        mutableStateOf(FakeDatabase().getFakeData())
    }

    val scrollState = rememberScrollState()

    Surface {

    }
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (data in dataList) {
                Selector(data,navController)
            }
        }
    }
}

@Composable
fun Selector(
    model: Model,
    navController: NavController
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
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
                verticalArrangement = Arrangement.Center,) {
                Text(
                    text = model.name,
                    fontSize = 16.sp,
                    style = TextStyle(
                        color = primaryTextColor
                    ),
                    fontFamily = textFont
                )
                Text(
                    text = model.description,
                    fontSize = 12.sp,
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
                    .padding(end = 5.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = "${model.current}$",
                        textAlign = TextAlign.End,
                        style = TextStyle(
                            color = primaryTextColor
                        ),
                        fontFamily = textFont
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Image(
                            painter = painterResource(
                                id = if(model.positiveGrowth) R.drawable.up else R.drawable.down),
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            text = "${model.growthPercent}%",
                            style = TextStyle(
                                color = secondaryTextColor
                            ),
                            fontFamily = textFont
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectorPreview() {
    UiAssignmentSeptemberTheme {
        Selector(model = FakeDatabase().getModelFromID(2).toModel(), navController = NavController(LocalContext.current))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UiAssignmentSeptemberTheme {
        HomeScreen(navController = NavController(LocalContext.current))
    }
}