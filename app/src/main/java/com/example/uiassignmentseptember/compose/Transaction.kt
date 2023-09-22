package com.example.uiassignmentseptember.compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.core.content.ContextCompat
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.Model
import com.example.uiassignmentseptember.model.toModel
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme

@Composable
fun Transaction(
    textFont:FontFamily,
    context: Context,
    model: Model
) {

    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    var sendingMoney by rememberSaveable {
        mutableStateOf("0")
    }
    val listOfInput = listOf("1","2","3","4","5","6","7","8","9",".","0","←")

    DisposableEffect(sendingMoney) {
        val moneyBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (p1 != null) {
                    val received = p1.getStringExtra("moneyEdit")
                    if (received == (0 until 9).toString()) {
                        if (!(sendingMoney == "0" && received == "0")) {
                            sendingMoney += received
                        }
                    } else if (received == "←") {
                        sendingMoney = sendingMoney.substring(0, sendingMoney.length - 1)
                    } else if (received == ".") {
                        if (sendingMoney.contains(".")) {
                            sendingMoney.replace(".","")
                        }
                        sendingMoney += "."
                    }
                }
            }
        }

        context.registerReceiver(
            moneyBroadcastReceiver,
            IntentFilter("moneyEdit"),
            Context.RECEIVER_NOT_EXPORTED
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
                        fontSize = 15.sp,
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
                    Row() {
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
                                    modifier = Modifier.padding(15.dp)
                                )

                                Text(
                                    text = getRawMoneyNumber(sendingMoney),
                                    style = TextStyle(
                                        color = secondaryColor
                                    ),
                                    fontSize = 18.sp,
                                    fontFamily = textFont,
                                    modifier = Modifier.padding(15.dp)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
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
                                    modifier = Modifier
                                ) {
                                    Text(
                                        text = " ${model.name}",
                                        style = TextStyle(
                                            color = secondaryColor
                                        ),
                                        fontSize = thisFontSize.sp,
                                        fontFamily = textFont,
                                        modifier = Modifier.padding(3.dp),
                                        onTextLayout = {textLayoutResult ->
                                            if (textLayoutResult.didOverflowWidth) {
                                                thisFontSize -= 1
                                            }
                                        }
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
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                        ) {
                            Spacer(modifier = Modifier.size(16.dp))

                            Text(
                                text = "Send",
                                style = TextStyle(
                                    color = primaryColor
                                ),
                                fontSize = 15.sp,
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

                            Spacer(
                                modifier = Modifier.width(10.dp)
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
    val result = (value.toDouble() * 20) * (value.toDouble() * 20)
    return "$$result"
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
            model = FakeDatabase().getModelFromID(2).toModel()
        )
    }
}

