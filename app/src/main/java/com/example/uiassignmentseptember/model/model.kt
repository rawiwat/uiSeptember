package com.example.uiassignmentseptember.model

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.model.LineChartData

data class Model(
    val id:Int,
    val name: String,
    val current: Double,
    val growthPercent: Double,
    val positiveGrowth:Boolean,
    val description: String,
    val imageId: Int,
    val detail: String,
    val pointData: List<Point>,
    val chartColor: Color
)
fun Model?.toModel(): Model = Model(
    this!!.id,
    this.name,
    this.current,
    this.growthPercent,
    this.positiveGrowth,
    this.description,
    this.imageId,
    this.detail,
    this.pointData,
    this.chartColor
)