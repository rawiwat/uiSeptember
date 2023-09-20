package com.example.uiassignmentseptember.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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

data class NavigationUIState(
    //--Back Stack for navigation--
    val navigationBackStack: SnapshotStateList<AppScreenTypes> = mutableStateListOf(AppScreenTypes.Screen1()),

    //--Horizontal Draggable Screen offsets--
    val screenXOffset: Float = 0.0f,
    val topScreenXOffset: Float = 0.0f,
    val prevScreenXOffset: Float = 0.0f,

    //-- Index of previous screen, this is used to fine tune animations.--
    val prevScreenIndex: Int = -1,
)

sealed class AppScreenTypes(val route: String, args: String? = null){
    class Screen1(args: String? = null): AppScreenTypes(route = "screen1", args = args)
    class Screen2(args: String? = null): AppScreenTypes(route = "screen1", args = args)
}
