package com.example.uiassignmentseptember.compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.toModel
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme

@Composable
fun Transaction(
    textFont:FontFamily,
    context: Context,
    modelId: Int
) {
    val model = FakeDatabase().getModelFromID(modelId).toModel()
    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    var sendingMoney by rememberSaveable {
        mutableStateOf("0")
    }
    val listOfInput = listOf("1","2","3","4","5","6","7","8","9",".","0","←")
    val singleDigitNum = listOf("0","1","2","3","4","5","6","7","8","9")
    DisposableEffect(sendingMoney) {
        val moneyBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (p1 != null) {
                    val received = p1.getStringExtra("moneyEdit").toString()
                    if (sendingMoney == "0") {
                        if (singleDigitNum.contains(received)) {
                            sendingMoney = received
                        } else if (received == ".") {
                            sendingMoney += "."
                        }
                    } else {
                        if (singleDigitNum.contains(received)) {
                            sendingMoney += received
                        } else if (received == ".") {
                            if (sendingMoney.contains(".")) {
                                sendingMoney.replace(".","")
                            }
                            sendingMoney += "."
                        } else if (received == "←") {
                            if (sendingMoney.length >= 2) {
                                sendingMoney = sendingMoney.substring(0,sendingMoney.length - 1)
                            } else {
                                sendingMoney = "0"
                            }
                        }
                    }
                }
            }
        }

        context.registerReceiver(
            moneyBroadcastReceiver,
            IntentFilter("moneyEdit"),
            Context.RECEIVER_EXPORTED
        )

        onDispose{
            context.unregisterReceiver(moneyBroadcastReceiver)
        }
    }
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(color = Color.Black)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = "Send",
                        style = TextStyle(
                            color = primaryColor
                        ),
                        fontSize = 20.sp,
                        fontFamily = textFont
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.DarkGray
                        )
                    ) {
                        Text(
                            text = " $ USD ",
                            style = TextStyle(
                                color = secondaryColor
                            ),
                            fontSize = 15.sp,
                            fontFamily = textFont,
                            modifier = Modifier.padding(3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black)
                        .padding(6.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Review Transfer",
                        fontSize = 22.sp,
                        fontFamily = textFont
                    )
                }
            }
        },
        modifier = Modifier.background(Color.Black)
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier.background(color = Color.Black)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(
                            start = 8.dp,
                            top = 8.dp,
                            end = 8.dp
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Row {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                        ) {
                            Column {
                                Text(
                                    text = sendingMoney,
                                    style = TextStyle(
                                        color = primaryColor
                                    ),
                                    fontSize = 30.sp,
                                    fontFamily = textFont,
                                    modifier = Modifier
                                        .padding(
                                            start = 15.dp,
                                            top = 10.dp
                                        )
                                        .height(36.dp)
                                )

                                Text(
                                    text = getRawMoneyNumber(sendingMoney),
                                    style = TextStyle(
                                        color = secondaryColor
                                    ),
                                    fontSize = 18.sp,
                                    fontFamily = textFont,
                                    modifier = Modifier
                                        .padding(start = 15.dp)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Card(
                                    modifier = Modifier,
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
                                                .rotate(180f)
                                                .size(16.dp)
                                        )

                                        Spacer(modifier = Modifier.width(2.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row() {
                                    Text(
                                        text ="Balance: 0.015",
                                        style = TextStyle(
                                            color = secondaryColor
                                        ),
                                        fontSize = 14.sp,
                                        fontFamily = textFont,
                                        modifier = Modifier.padding(3.dp)
                                    )
                                    Text(
                                        text = "Max",
                                        style = TextStyle(
                                            color = Color.Yellow
                                        ),
                                        fontSize = 14.sp,
                                        fontFamily = textFont,
                                        modifier = Modifier.padding(3.dp)
                                    )
                                }
                            }


                            Spacer(
                                modifier = Modifier.width(10.dp)
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(
                            start = 8.dp,
                            top = 4.dp,
                            end = 8.dp
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var thisFontSize by rememberSaveable {
                                    mutableIntStateOf(15)
                                }
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
                                        color = primaryColor
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
                                        .rotate(180f)
                                        .size(16.dp)
                                )

                                Spacer(modifier = Modifier.width(2.dp))
                            }
                            Text(
                                text = "47 previous transfers",
                                style = TextStyle(
                                    color = secondaryColor
                                ),
                                fontSize = 12.sp,
                                fontFamily = textFont,
                                modifier = Modifier.padding(3.dp),
                            )
                        }
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Card(
                    modifier = Modifier.size(40.dp),
                    border = BorderStroke(
                        width = 4.dp,
                        color = Color.Black
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    content = {
                        items(listOfInput) {
                            NumberPanel(
                                value = it,
                                context = context,
                                textColor = primaryColor,
                                textFont = textFont
                            )
                        }
                    }
                )
            }
        }
    }
}

fun getRawMoneyNumber(
    value: String
): String {
    val valueAsNum = value.toDouble()
    return if (valueAsNum > 0) {
        ((value.toDouble() * 10) * (value.toDouble() * 10)).toString()
    } else {
        ""
    }
}
@Composable
fun NumberPanel(
    value: String,
    context: Context,
    textColor: Color,
    textFont: FontFamily
) {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp / 3
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                val intent = Intent("moneyEdit")
                intent.putExtra("moneyEdit", value)
                context.sendBroadcast(intent)
            }
            .width(width.dp)
            .height((width / 1.25).dp)
    ) {
        Text(
            text = value,
            style = TextStyle(
                color = textColor
            ),
            fontSize = 20.sp,
            fontFamily = textFont
        )
    }
}
@Preview
@Composable
fun PreviewTransaction() {
    UiAssignmentSeptemberTheme {
        Transaction(
            textFont = FontFamily(Font(R.font.impact)),
            context = LocalContext.current,
            modelId = 2
        )
    }
}

@Preview
@Composable
fun PreviewTransaction2() {
    UiAssignmentSeptemberTheme {
        Transaction(
            textFont = FontFamily(Font(R.font.impact)),
            context = LocalContext.current,
            modelId = 1
        )
    }
}

