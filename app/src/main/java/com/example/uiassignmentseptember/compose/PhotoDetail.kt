package com.example.uiassignmentseptember.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.uiassignmentseptember.model.FakeDatabase
import com.example.uiassignmentseptember.model.toModel

@Composable
fun PhotoDetail(
    modelId:Int,
    photoId:Int
) {
    val model by remember {
        mutableStateOf(FakeDatabase().getModelFromID(modelId).toModel())
    }
    
}