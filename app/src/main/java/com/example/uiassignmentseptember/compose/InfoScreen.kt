package com.example.uiassignmentseptember.compose


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.Model
import com.example.uiassignmentseptember.model.toModel


@Preview
@Composable
fun InfoScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Graph(model = FakeDatabase().getModelFromID(1).toModel())
    }
}

@Composable
fun Graph(model: Model) {
    LineChart(
        modifier = Modifier,
        lineChartData = model.chartData)
}

fun generateChartData(
    pointsData:List<Point>,
    bgColor: Color,
    xAxisData:AxisData,
    yAxisData:AxisData,
):LineChartData {
    return LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = bgColor
    )
}

fun generateAXizX(
    pointsData:List<Point>,
    stepSize:Int,
    axisPadding:Int,
    bgColor: Color
): AxisData {
    return AxisData.Builder()
        .axisStepSize(stepSize.dp)
        .backgroundColor(bgColor)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(axisPadding.dp)
        .build()
}

fun generateAxizY(
    stepSize: Int,
    axisPadding:Int,
    bgColor: Color
):AxisData {
    return AxisData.Builder()
        .steps(stepSize)
        .backgroundColor(Color.Red)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 100 / stepSize
            (i * yScale).toString()
        }.build()
}