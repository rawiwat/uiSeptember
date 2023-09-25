package com.example.uiassignmentseptember.compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.Model
import com.example.uiassignmentseptember.model.toModel
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme

@Composable
fun Transaction(
    textFont:FontFamily,
    context: Context,
    modelId: Int
) {
    var model by remember {
        mutableStateOf(FakeDatabase().getModelFromID(modelId).toModel())
    }
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

    DisposableEffect(model) {
        val modelBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val idReceived = p1!!.getIntExtra("change_model_to",model.id)
                model = FakeDatabase().getModelFromID(idReceived).toModel()
            }
        }

        context.registerReceiver(
            modelBroadcastReceiver,
            IntentFilter("change_model"),
            Context.RECEIVER_EXPORTED
        )

        onDispose{
            context.unregisterReceiver(modelBroadcastReceiver)
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
                                    modifier = Modifier
                                        .clickable {
                                        val intent = Intent("Change_activate")
                                        intent.putExtra("change_activation_to",true)
                                        context.sendBroadcast(intent)
                                        },
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

                                Row {
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
                                    painter = painterResource(id = R.drawable.baraka_obama),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop

                                )
                                Text(
                                    text = " Baraka Obama",
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
                                    painter = painterResource(id = R.drawable.point_back_bright),
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
    ChangeModel(context = context)
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
    val height = configuration.screenHeightDp / 8

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
            .height(height.dp)
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

@Composable
fun ChangeModel(
    context:Context
) {
    val configuration = LocalConfiguration.current
    var offset by rememberSaveable {
        mutableIntStateOf(configuration.screenHeightDp / 2)
    }

    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val listOfTokenImageId = listOf(
        R.drawable.token_dgp,
        R.drawable.token_dr,
        R.drawable.token_discord,
        R.drawable.token_doritos,
        R.drawable.token_jgp,
        R.drawable.token_unity
    )

    var active by remember {
        mutableStateOf(false)
    }

    DisposableEffect(active) {
        val activationReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val activationReceived = p1!!.getBooleanExtra("change_activation_to", false)
                offset = (configuration.screenHeightDp / 2)
                active = activationReceived
            }
        }
        context.registerReceiver(
            activationReceiver,
            IntentFilter("Change_activate"),
            Context.RECEIVER_EXPORTED
        )
        onDispose {
        context.unregisterReceiver(activationReceiver)
        }
    }

    AnimatedVisibility(visible = active) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .offset(0.dp, offset.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Canvas(
                    modifier = Modifier
                        .width(50.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                offset += dragAmount.y.toInt()
                                if (offset < 0) {
                                    offset = 0
                                }

                                if (offset >= (configuration.screenHeightDp * 3 / 4)) {
                                    offset = (configuration.screenHeightDp / 2)
                                    active = false
                                }
                            }
                        },
                ) {
                    val canvasWidth = size.width

                    drawLine(
                        start = Offset(x = canvasWidth, y = 0f),
                        end = Offset(x = 0f, y = 0f),
                        color = Color.Gray,
                        strokeWidth = 7f
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(
                            start = 8.dp,
                            end = 8.dp
                        ),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.DarkGray,
                        containerColor = Color.Gray,
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))

                        Image(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Search tokens"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your tokens",
                        style = TextStyle(
                            color = secondaryColor
                        ),
                        fontSize = 20.sp,
                        fontFamily = textFont
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Token(
                            imageId = listOfTokenImageId[0],
                            offsetX = 25
                        )

                        Token(
                            imageId = listOfTokenImageId[1],
                            offsetX = 20
                        )

                        Token(
                            imageId = listOfTokenImageId[2],
                            offsetX = 15
                        )

                        Token(
                            imageId = listOfTokenImageId[3],
                            offsetX = 10
                        )

                        Token(
                            imageId = listOfTokenImageId[4],
                            offsetX = 5
                        )

                        Token(
                            imageId = listOfTokenImageId[5],
                            offsetX = 0
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Image(
                            painter = painterResource(id = R.drawable.point_back),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .rotate(-90f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                ModelOption(
                    model = FakeDatabase().getModelFromID(1).toModel(),
                    context = context
                )

                ModelOption(
                    model = FakeDatabase().getModelFromID(2).toModel(),
                    context = context
                )

                ModelOption(
                    model = FakeDatabase().getModelFromID(3).toModel(),
                    context = context
                )

                ModelOption(
                    model = FakeDatabase().getModelFromID(4).toModel(),
                    context = context
                )

                ModelOption(
                    model = FakeDatabase().getModelFromID(5).toModel(),
                    context = context
                )
            }
        }
    }
}

@Composable
fun ModelOption(
    model: Model,
    context: Context
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val topFontSize = 16.sp
    val bottomFontSize = 14.sp

    Surface(
        modifier = Modifier
            .clickable {
                val intent = Intent("Change_activate")
                intent.putExtra("change_activation_to", false)
                context.sendBroadcast(intent)
                val changeModel = Intent("change_model")
                changeModel.putExtra("change_model_to", model.id)
                context.sendBroadcast(changeModel)
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
                            text = getRawMoneyNumber(model.current.toString()),
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
                            Text(
                                text = "$${model.current}",
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
}

@Composable
fun Token(
    imageId:Int,
    offsetX:Int
) {
    Card(
        modifier = Modifier
            .size(16.dp)
            .offset(x = offsetX.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.teal_200)
        )
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,

            )
    }

}

/*@Preview
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
fun PreviewModelChanger() {
    UiAssignmentSeptemberTheme {
        ChangeModel(
            LocalContext.current
        )
    }
}*/

@Preview(showBackground = true)
@Composable
fun PreviewModelSelecter() {
    UiAssignmentSeptemberTheme {
        ModelOption(model = FakeDatabase().getModelFromID(2).toModel(), context = LocalContext.current)
    }
}