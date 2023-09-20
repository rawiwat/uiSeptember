package com.example.uiassignmentseptember.compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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

@Composable
fun InfoScreen(id: Int,context: Context) {
    val model = FakeDatabase().getModelFromID(id).toModel()
    var readMore by rememberSaveable {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.Black)
        ) {
            Graph(model = model,context)
            Text(
                text = model.detail,
                maxLines = if (readMore) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = if (readMore) "Show Less" else "Read More",
                modifier = Modifier.clickable { readMore = !readMore }
            )
        }
    }
}

@Composable
fun Graph(
    model: Model,
    context: Context
) {
    var currentOutPut by rememberSaveable {
        mutableStateOf(GraphOutputType.YEAR)
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
            Button(
                onClick = { currentOutPut = GraphOutputType.HOUR }
            ) {
                Text(text = "1H")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { currentOutPut = GraphOutputType.DAY }
            ) {
                Text(text = "1D")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { currentOutPut = GraphOutputType.WEEK }
            ) {
                Text(text = "1W")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { currentOutPut = GraphOutputType.MONTH }
            ) {
                Text(text = "1M")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { currentOutPut = GraphOutputType.YEAR },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentOutPut == GraphOutputType.YEAR) {
                        colorResource(R.color.teal_700)
                    } else { Color.Black },
                    contentColor = colorResource(R.color.teal_200)
                )
            ) {
                Text(text = "1Y")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoPreview() {
    InfoScreen(id = 1, LocalContext.current)
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
                        color = lineColor,
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
            intent.putExtra("ChangeGraphOutput",thisType)
            context.sendBroadcast(intent)
                  },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (currentType == GraphOutputType.YEAR) {
                colorResource(R.color.teal_700)
            } else { Color.Black },
            contentColor = colorResource(R.color.teal_200)
        )
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