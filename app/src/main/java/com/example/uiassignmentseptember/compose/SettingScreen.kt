package com.example.uiassignmentseptember.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.walletCode

@Composable
fun SettingScreen(
    navController: NavController
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))

    Scaffold(
        topBar = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.Black)
            ) {
                val (settingText,arrow) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.point_back),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { navController.navigate("Home") }
                        .size(20.dp)
                        .constrainAs(arrow) {
                            start.linkTo(parent.start, margin = 15.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Text(
                    text = "Settings",
                    fontSize = 16.sp,
                    style = TextStyle(
                        color = primaryTextColor
                    ),
                    fontFamily = textFont,
                    modifier = Modifier.constrainAs(settingText) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = Color.Black)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                SettingHeader(text = "Wallet Setting")
                SettingWallet(text = "Obama")
                SettingWallet(text = "Joe Biden")

                Spacer(modifier = Modifier.height(10.dp))

                SettingComponent(
                    headerText = "App setting",
                    imageId1 = R.drawable.dark_or_light_mode_icon,
                    settingText1 = "Appearance",
                    endText1 = "Dark mode",
                    imageId2 = R.drawable.face_id_icon,
                    settingText2 = "Face ID",
                    endText2 = ""
                )

                Spacer(modifier = Modifier.height(10.dp))

                SettingComponent(
                    headerText = "Support",
                    imageId1 = R.drawable.feedback_icon,
                    settingText1 = "Send Feedback",
                    endText1 = "",
                    imageId2 = R.drawable.help_icon,
                    settingText2 = "Get Help",
                    endText2 = ""
                )

                Spacer(modifier = Modifier.height(10.dp))

                SettingComponent(
                    headerText = "About",
                    imageId1 = R.drawable.privacy_icon,
                    settingText1 = "Privacy Policy",
                    endText1 = "",
                    imageId2 = R.drawable.term_of_services_icon,
                    settingText2 = "Terms of Services",
                    endText2 = ""
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Version 0.1.7",
                    fontSize = 15.sp,
                    style = TextStyle(
                        color = Color.DarkGray
                    ),
                    fontFamily = FontFamily(Font(R.font.impact)),
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        }

    }
}

@Composable
fun SettingHeader(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        style = TextStyle(
            color = colorResource(id = R.color.teal_700)
        ),
        fontFamily = FontFamily(Font(R.font.impact)),
        modifier = Modifier.padding(start = 15.dp)
    )
}

@Composable
fun SettingBody(
    imageId: Int,
    text: String,
    endText: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.Black)
    ) {
        val (mainText,arrow,image,secondText) = createRefs()

        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .constrainAs(image) {
                    start.linkTo(parent.start, margin = 15.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = text,
            fontSize = 16.sp,
            style = TextStyle(
                color = colorResource(id = R.color.teal_200)
            ),
            fontFamily = FontFamily(Font(R.font.impact)),
            modifier = Modifier.constrainAs(mainText) {
                start.linkTo(image.end, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.point_back),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(arrow) {
                    end.linkTo(parent.end, margin = 15.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .rotate(180f)
        )

        Text(
            text = endText,
            fontSize = 16.sp,
            style = TextStyle(
                color = colorResource(id = R.color.teal_700)
            ),
            fontFamily = FontFamily(Font(R.font.impact)),
            modifier = Modifier.constrainAs(secondText) {
                end.linkTo(arrow.start, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
            }
        )
    }
}

@Composable
fun SettingWallet(
    text: String
) {
    val walletId by rememberSaveable {
        mutableStateOf(walletCode(text))
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.Black)
    ) {
        val (mainText,arrow,image,bottomText) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.wallet_icon),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .constrainAs(image) {
                    start.linkTo(parent.start, margin = 15.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = text,
            fontSize = 16.sp,
            style = TextStyle(
                color = colorResource(id = R.color.teal_200)
            ),
            fontFamily = FontFamily(Font(R.font.impact)),
            modifier = Modifier.constrainAs(mainText) {
                start.linkTo(image.end, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = text,
            fontSize = 16.sp,
            style = TextStyle(
                color = colorResource(id = R.color.teal_700)
            ),
            fontFamily = FontFamily(Font(R.font.impact)),
            modifier = Modifier.constrainAs(mainText) {
                start.linkTo(image.end, margin = 8.dp)
                bottom.linkTo(bottomText.top)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = walletId,
            fontSize = 16.sp,
            style = TextStyle(
                color = colorResource(id = R.color.teal_700)
            ),
            fontFamily = FontFamily(Font(R.font.impact)),
            modifier = Modifier.constrainAs(mainText) {
                start.linkTo(image.end, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                top.linkTo(mainText.bottom)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.point_back),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(arrow) {
                    end.linkTo(parent.end, margin = 15.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .rotate(180f)
        )
    }
}

@Composable
fun SettingComponent(
    headerText: String,
    imageId1: Int,
    settingText1: String,
    endText1: String,
    imageId2: Int,
    settingText2: String,
    endText2: String
) {
    Column {
        SettingHeader(text = headerText)
        SettingBody(imageId = imageId1, text = settingText1, endText = endText1)
        SettingBody(imageId = imageId2, text = settingText2, endText = endText2)
    }
}
@Preview
@Composable
fun PreviewSetting() {
    SettingScreen(navController = NavController(LocalContext.current))
}