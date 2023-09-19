package com.example.uiassignmentseptember.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uiassignmentseptember.R
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.Model
import com.example.uiassignmentseptember.model.toModel
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme

@Composable
fun HomeScreen(
    navController: NavController
) {
    val dataList = FakeDatabase().getFakeData()
    LazyColumn() {
        items(dataList) {
            Selector(it,navController)
        }
    }
}

@Composable
fun Selector(
    model: Model,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate("infoScreen/${model.id}")
            }
            .size(width = 100.dp,height = 75.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = model.imageId),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectorPreview() {
    UiAssignmentSeptemberTheme {
        Selector(model = FakeDatabase().getModelFromID(2).toModel(), navController = NavController(LocalContext.current))
    }
}