package com.example.uiassignmentseptember

import android.content.Context
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import java.util.SortedMap
import kotlin.random.Random

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

fun getRandomName():String {
    return listOf("Lilyana Hendrix",
            "Korbyn Pittman",
            "Marie Nash",
            "Chandler Huber",
            "Raquel Skinner",
            "Ridge Mack",
            "Nadia Gray",
            "Nicholas Gentry",
            "Amelie Ahmed",
            "Harry Vaughan",
            "Nancy Schmitt",
            "Murphy Hurley",
            "Rylan Mullen",
            "Shepard Santos",
            "Alana Flowers",
            "Saul Miles",
            "Alessandra Turner",
            "Joshua Bartlett",
            "Aubrielle Reilly",
            "Alvaro Lynn",
            "Samira Hoffman",
            "Steven Walsh",
            "Leia Donovan",
            "Brayan Neal",
            "Talia Newton",
            "Santino Shields",
            "Analia Hardin",
            "Hassan Salinas",
            "Royalty Moses",
            "Niklaus Lawrence",
            "Lauren Kent",
            "Mekhi Francis",
            "Daniella Frost",
            "Dario Dominguez",
            "Raegan Bernal",
            "Eithan Colon",
            "Remy Boyd",
            "Dean Dunlap",
            "Iliana Sanders",
            "Jose McIntyre",
            "Rebekah Keith",
            "Jagger Turner",
            "Brooklyn Cain",
            "Benson Christensen",
            "Carmen Randolph",
            "Eugene Whitney",
            "Madalynn Nolan",
            "Maximo Frazier",
            "Octavia Thompson",
            "Theodore Bauer",
            "Haley Johnson",
            "Noah Portillo",
            "Nathalie Guevara",
            "Tommy Knox").random()
}

enum class Trait(val type: String) {
    HAT("Hat"),
    BODY("Body"),
    EYES("Eyes"),
    MOUTH("Mouth"),
    EAR("Ear"),
    ARM("Arm"),
    LEG("Leg")
}

fun traitsValueGenerator(traitType: Trait):String {
    return when(traitType) {
        Trait.HAT -> listOf("Chef","Fedora","Cap","Top Hat","Deerstalker").random()
        Trait.BODY -> listOf("Suit", "T-Shirt", "Pajamas", "Sleeveless","Blouse").random()
        Trait.EYES -> listOf("Black","Blue","Red","Brown","Green").random()
        Trait.MOUTH -> listOf("Bow","Heart","Full","Wide","Top-Heavy").random()
        Trait.EAR -> listOf("Pointy","Square","Broad","Protruding","Pierced").random()
        Trait.ARM -> listOf("Glove","Ring","Watch","Band","Armlet").random()
        Trait.LEG -> listOf("Legging","Jeans","Pajamas","Pants","Jeggings").random()
    }
}

enum class ActivityTypes(val typeName: String) {
    SENT("Sent"),
    MINTED("Minted"),
    SWAPPED("Swapped"),
    TRANSACTION_CONFIRM("Transaction confirm"),
    SOLD("Sold"),
    RECEIVED("Received"),
    APPROVED("Approved")
}

fun generateActivityDetail(type:ActivityTypes):String {
    val currency = listOf("ETH","BCC","BCH","BTC","DASH","ZEC","USDC")
    return when(type) {
            ActivityTypes.SENT -> listOf("${getRandomName()} #${Random.nextInt(0,9999)} to ${getRandomName()}","${Random.nextDouble(0.001,2.0)} ${currency.random()} to ${getRandomName()}").random()
            ActivityTypes.MINTED -> getRandomName()
            ActivityTypes.SWAPPED -> "${Random.nextDouble(0.001,2.0)} ${currency.random()} → ${Random.nextDouble(0.001,2.0)} ${currency.random()}"
            ActivityTypes.TRANSACTION_CONFIRM -> getRandomName()
            ActivityTypes.SOLD -> "${getRandomName()} #${Random.nextInt(0,9999)}"
            ActivityTypes.RECEIVED -> "${Random.nextDouble(0.001,1.0)} ${currency.random()} from ${getRandomName()}"
            ActivityTypes.APPROVED -> "${Random.nextDouble(0.001,2.0)} "
        }
}

data class ActivityTime(
    val text:String,
    val value:Int
)

fun getRandomTime(): ActivityTime {
    val amORpm = listOf("pm","am").random()
    val hour = Random.nextInt(1,12)
    val minutes = Random.nextInt(0,59)
    var value = (hour * 60) + minutes
    if (amORpm == "pm") {
        value += 720
    }
    return ActivityTime(
        text = "$hour:$minutes$amORpm",
        value = value
    )
}

data class CryptoActivity(
    val type: String,
    val detail: String,
    val time: ActivityTime,
    val date: Date
)

data class Date(val month: Month, val day: Int)

enum class Month(val monthName:String, val day: Int,val order:Int) {
    JANUARY("January",30,1),
    FEBRUARY("February",28,2),
    MARCH("March",31,3),
    APRIL("April",30,4),
    MAY("May",31,5),
    JUNE("June",30,6),
    JULY("July",31,7),
    AUGUST("August",31,8),
    SEPTEMBER("September",30,9),
    OCTOBER("October",31,10),
    NOVEMBER("November",30,11),
    DECEMBER("December",31,12)
}

fun generateRandomDate(): Date {
    val month = Month.values().toList().random()
    return Date(month = month, day = Random.nextInt(1,month.day))
}

fun generateActivity():CryptoActivity {
    val type = ActivityTypes.values().toList().random()
    return CryptoActivity(
        type = type.typeName,
        detail = generateActivityDetail(type),
        date = generateRandomDate(),
        time = getRandomTime()
    )
}

fun generateRecord(): SortedMap<Month, List<CryptoActivity>> {
    val result = mutableListOf<CryptoActivity>()
    repeat(100) {
        result.add(generateActivity())
    }

    return result.groupBy { it.date.month }.toSortedMap()
}

data class Categorized(
    val month: String,
    val activities: List<CryptoActivity>
)

enum class SwapScreenType() {
    
}