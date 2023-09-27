package com.example.uiassignmentseptember

import android.content.Context
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point

enum class GraphOutputType {
    HOUR,DAY,WEEK,MONTH,YEAR
}

fun getTokenOffset(id: Int):Int {
    return when(id) {
        R.drawable.token_dgp -> 25
        R.drawable.token_dr -> 20
        R.drawable.token_discord -> 15
        R.drawable.token_doritos -> 10
        R.drawable.token_jgp -> 5
        R.drawable.token_unity -> 0
        else -> 0
    }
}

fun generateAXisX(
    pointsData: List<Point>,
    stepSize: Int
): AxisData {
    return AxisData.Builder()
        .axisStepSize(stepSize.dp)
        .backgroundColor(androidx.compose.ui.graphics.Color.Black)
        .steps(pointsData.size - 1)
        //.labelData { i -> i.toString() }
        //.labelAndAxisLinePadding(axisPadding.dp)
        .build()
}

fun generateAxisY(
    stepSize: Int
): AxisData {
    return AxisData.Builder()
        .steps(stepSize)
        .backgroundColor(androidx.compose.ui.graphics.Color.Black)
        //.labelAndAxisLinePadding(axisPadding.dp)
        //.labelData { i ->
        //    val yScale = 100 / stepSize
        //    (i * yScale).toString()
        //}
        .build()
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

fun getImageIds(context: Context): List<Int> {
    val imageIds = mutableListOf<Int>()
    val packageName = context.packageName
    val resources = context.resources
    val drawableClass = R.drawable::class.java

    try {
        val fields = drawableClass.fields
        for (field in fields) {
            val resourceName = field.name
            val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
            if (resourceId != 0) {
                imageIds.add(resourceId)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return imageIds
}