package com.example.uiassignmentseptember

import android.content.Context
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import com.example.uiassignmentseptember.model.CryptoActivity
import com.example.uiassignmentseptember.model.Date
import com.example.uiassignmentseptember.model.Hour
import com.example.uiassignmentseptember.model.Time
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
            ActivityTypes.SENT -> listOf("${getRandomName()} #${Random.nextInt(0,9999)} to ${getRandomName()}","${trimDouble(Random.nextDouble(0.001,2.0))} ${currency.random()} to ${getRandomName()}").random()
            ActivityTypes.MINTED -> getRandomName()
            ActivityTypes.SWAPPED -> "${trimDouble(Random.nextDouble(0.001,2.0))} ${currency.random()} → ${trimDouble(Random.nextDouble(0.001,2.0))} ${currency.random()}"
            ActivityTypes.TRANSACTION_CONFIRM -> getRandomName()
            ActivityTypes.SOLD -> "${getRandomName()} #${Random.nextInt(0,9999)}"
            ActivityTypes.RECEIVED -> "${trimDouble(Random.nextDouble(0.001,2.0))} ${currency.random()} from ${getRandomName()}"
            ActivityTypes.APPROVED -> "${trimDouble(Random.nextDouble(0.001,2.0))} ${currency.random()}"
        }
}

fun trimDouble(doubleValue: Double): Double {
    return String.format("%.3f", doubleValue).toDouble()
}

fun getRandomTime(): Hour {
    val amORpm = listOf("pm","am").random()
    val hour = Random.nextInt(1,12)
    val minutes = Random.nextInt(0,59)
    val minutesString = if (minutes >= 10) minutes.toString() else "0$minutes"
    var value = (hour * 60) + minutes
    if (amORpm == "pm") {
        value += 720
    }
    return Hour(
        text = "$hour:$minutesString$amORpm",
        value = value
    )
}



enum class Month(val day: Int) {
    Today(30),
    September(30),
    August(31),
    July(31),
    June(30),
    May(31),
    April(30),
    March(31),
    February(28),
    January(31),
    //OCTOBER("October",31,10),
    //NOVEMBER("November",30,11),
    //DECEMBER("December",31,12)
}

fun generateRandomDate(): Date {
    val month = Month.values().toList().random()
    return Date(month = month, day = Random.nextInt(1,month.day))
}

fun generateActivity(id: Int): CryptoActivity {
    val type = ActivityTypes.values().toList().random()
    return CryptoActivity(
        type = type.typeName,
        detail = generateActivityDetail(type),
        time = Time(
            generateRandomDate(),
            getRandomTime()
        ),
        id = id
    )
}

fun generateRecord(): SortedMap<Month, List<CryptoActivity>> {
    var currentId = 0
    val result = mutableListOf<CryptoActivity>()
    repeat(100) {
        val newActivity = generateActivity(currentId)
        result.add(newActivity)
        currentId += 1
    }
    return result.groupBy { it.time.date.month }.toSortedMap()
}

data class Categorized(
    val month: String,
    val activities: List<CryptoActivity>
)

enum class SwapScreenType {
    TOKEN,
    NFTS,
    ACTIVITY
}