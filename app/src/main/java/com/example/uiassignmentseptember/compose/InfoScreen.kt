package com.example.uiassignmentseptember.compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.uiassignmentseptember.GraphOutputType
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.Model
import com.example.uiassignmentseptember.model.toModel
import kotlin.math.roundToInt


@Composable
fun InfoScreen(
    id: Int,
    context: Context,
    navController: NavController
    //swipeableViewModel: SwipeableViewModel,
    //screenStackIndex: Int
) {
    val model = FakeDatabase().getModelFromID(id).toModel()
    var readMore by rememberSaveable {
        mutableStateOf(false)
    }
    var offsetX by remember { mutableFloatStateOf(0f) }
    val scrollState = rememberScrollState()

    var isFavorite by rememberSaveable {
        mutableStateOf(false)
    }
    val topIconSize = 20.dp
    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val bodyFontSize = 14.sp
    val splitedMoney = model.current.toString().split(".")

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(color = Color.Black),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.point_back),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { navController.navigate("Home") }
                            .size(topIconSize)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "$${model.current}",
                        style = TextStyle(
                            color = primaryColor
                        ),
                        fontFamily = textFont
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.three_dots),
                        contentDescription = null,
                        modifier = Modifier.size(topIconSize)
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Image(
                        painter = painterResource(
                            id = if (isFavorite) {
                                R.drawable.fav_is_selected
                            } else { R.drawable.fav_not_selected }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { isFavorite = !isFavorite }
                            .size(topIconSize)
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                }
            }

        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                /*.pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        if (dragAmount.x >= 50f || dragAmount.x <= 50f ) {
                            offsetX += dragAmount.x
                        }
                        if (offsetX >= 100f || offsetX <= 100f) {
                            navController.navigate("Home")
                        }
                    }
                }*/
                .padding(innerPadding)
        ) {
            if (scrollState.isScrollInProgress){
                println("Scrolling:${scrollState.value}")
            }
            Column(
                modifier = Modifier
                    .background(color = Color.Black)
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                Column(
                    modifier = Modifier.padding(start = 8.dp)
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

                    Text(
                        text = model.name,
                        style = TextStyle(
                            color = primaryColor
                        ),
                        fontFamily = textFont,
                        fontSize = 13.sp
                    )

                    Row() {
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
                            text = "${model.growthPercent}%",
                            style = TextStyle(
                                color = if (model.positiveGrowth) {
                                    Color.Green
                                } else {
                                    Color.Red
                                }
                            ),
                            fontFamily = textFont,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Graph(model = model,context)

                Text(
                    text = model.detail,
                    maxLines = if (readMore) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(color = Color.White),
                    modifier = Modifier.animateContentSize(animationSpec = tween(1000))
                )

                Text(
                    text = if (readMore) "Show Less" else "Read More",
                    modifier = Modifier
                        .clickable { readMore = !readMore },
                    style = TextStyle(color = colorResource(id = R.color.teal_200))
                )
            }
        }
    }
    
}

@Composable
fun Graph(
    model: Model,
    context: Context
) {
    var currentOutPut by rememberSaveable {
        mutableStateOf(GraphOutputType.WEEK)
    }

    DisposableEffect(currentOutPut){
        val graphTypeReceiver = object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                val received = p1?.getStringExtra("ChangeGraphOutput")
                currentOutPut = GraphOutputType.valueOf(received.toString())
            }
        }
        context.registerReceiver(graphTypeReceiver, IntentFilter("ChangeGraphOutput"), Context.RECEIVER_NOT_EXPORTED)
        onDispose {
            context.unregisterReceiver(graphTypeReceiver)
        }
    }

    Column {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            lineChartData = generateChartData(
                pointsData = getPointData(currentOutPut,model.pointData),
                lineColor = model.chartColor,
                xAxisData = generateAXisX(
                    getPointData(currentOutPut,model.pointData),
                    getAxisXStepSize(currentOutPut),
                    5
                ),
                yAxisData = generateAxisY(20,5)
            ),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            GraphOutPutSelector(
                currentType = currentOutPut,
                thisType = GraphOutputType.HOUR,
                buttonText = "1H",
                context = context
            )

            Spacer(modifier = Modifier.width(10.dp))

            GraphOutPutSelector(
                currentType = currentOutPut,
                thisType = GraphOutputType.DAY,
                buttonText = "1D",
                context = context
            )

            Spacer(modifier = Modifier.width(10.dp))

            GraphOutPutSelector(
                currentType = currentOutPut,
                thisType = GraphOutputType.WEEK,
                buttonText = "1W",
                context = context
            )

            Spacer(modifier = Modifier.width(10.dp))

            GraphOutPutSelector(
                currentType = currentOutPut,
                thisType = GraphOutputType.MONTH,
                buttonText = "1M",
                context = context
            )

            Spacer(modifier = Modifier.width(10.dp))

            GraphOutPutSelector(
                currentType = currentOutPut,
                thisType = GraphOutputType.YEAR,
                buttonText = "1Y",
                context = context
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoPreview() {
    InfoScreen(id = 1, LocalContext.current, navController = NavController(LocalContext.current))
}

fun generateChartData(
    pointsData: List<Point>,
    lineColor: Color,
    xAxisData: AxisData,
    yAxisData: AxisData,
):LineChartData {
    return LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = lineColor,
                        lineType = LineType.SmoothCurve(),
                        width = 3f
                    ),
                    IntersectionPoint(
                        color = lineColor,
                        radius = 1.dp
                    ),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                lineColor,
                                lineColor,
                                Color.Transparent,
                            )
                        ),
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = Color.Black
    )
}

@Composable
fun GraphOutPutSelector(
    currentType: GraphOutputType,
    thisType: GraphOutputType,
    buttonText: String,
    context: Context
) {
    Button(
        onClick = {
            val intent = Intent("ChangeGraphOutput")
            intent.putExtra("ChangeGraphOutput",thisType.name)
            context.sendBroadcast(intent)
                  },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (currentType == thisType) {
                colorResource(R.color.teal_700)
            } else { Color.Black },
            contentColor = if (currentType == thisType) {
                colorResource(id = R.color.teal_200)
            } else { colorResource(id = R.color.teal_700) }
        ),
//        border = BorderStroke(3.dp,Color.Cyan)
    ) {
        Text(text = buttonText)
    }
}

fun generateAXisX(
    pointsData: List<Point>,
    stepSize: Int,
    axisPadding: Int
): AxisData {
    return AxisData.Builder()
        .axisStepSize(stepSize.dp)
        .backgroundColor(Color.Black)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(axisPadding.dp)
        .build()
}

fun generateAxisY(
    stepSize: Int,
    axisPadding: Int
):AxisData {
    return AxisData.Builder()
        .steps(stepSize)
        .backgroundColor(Color.Black)
        .labelAndAxisLinePadding(axisPadding.dp)
        .labelData { i ->
            val yScale = 100 / stepSize
            (i * yScale).toString()
        }.build()
}

fun getAxisXStepSize(type: GraphOutputType) :Int {
    return when(type) {
        GraphOutputType.HOUR -> 75
        GraphOutputType.DAY -> 50
        GraphOutputType.WEEK -> 25
        GraphOutputType.MONTH -> 10
        GraphOutputType.YEAR -> 3
    }
}

fun getPointData(type: GraphOutputType, fullData:List<Point>):List<Point> {
    return when(type) {
        GraphOutputType.HOUR -> fullData.subList(0,8)
        GraphOutputType.DAY -> fullData.subList(0,12)
        GraphOutputType.WEEK -> fullData.subList(0,24)
        GraphOutputType.MONTH -> fullData.subList(0,60)
        GraphOutputType.YEAR -> fullData.subList(0,200)
    }
}