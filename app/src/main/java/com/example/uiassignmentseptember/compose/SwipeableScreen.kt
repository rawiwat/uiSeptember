package com.example.uiassignmentseptember.compose

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import com.example.uiassignmentseptember.model.AppScreenTypes
import com.example.uiassignmentseptember.viewModel.SwipeableViewModel
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun HorizontalDraggableScreen(
    screenStackIndex: Int = 0,
    swipeableViewModel: SwipeableViewModel,
    content: @Composable (ColumnScope.() -> Unit),

    ){

    val navigationUiState = swipeableViewModel.navigationState.collectAsState()
    val screenXOffset = navigationUiState.value.screenXOffset
    val topScreenXOffset = navigationUiState.value.topScreenXOffset
    val prevScreenXOffset = navigationUiState.value.prevScreenXOffset


    val prevScreenIndex = navigationUiState.value.prevScreenIndex
    val animatedTopXOffset = animateFloatAsState(targetValue = topScreenXOffset)
    val animatedPrevXOffset = animateFloatAsState(targetValue = prevScreenXOffset)
    val navigationBackStack = navigationUiState.value.navigationBackStack
    val isTopScreen = screenStackIndex == navigationBackStack.lastIndex && screenStackIndex != prevScreenIndex

    LaunchedEffect(key1 = navigationBackStack.size){
        if(prevScreenIndex == screenStackIndex){
            delay(500)
            swipeableViewModel.resetScreenXOffset()
        }
    }

    DisposableEffect(key1 = Unit){
        onDispose {
            swipeableViewModel.cleanUpXOffset()
        }
    }

    Column(
        content = content,
        modifier = Modifier
            .offset {
                if (isTopScreen) {
                    IntOffset(animatedTopXOffset.value.roundToInt(), 0)
                } else {
                    IntOffset(animatedPrevXOffset.value.roundToInt(), 0)
                }
            }
            .draggable(
                enabled = ((screenStackIndex != 0) && prevScreenIndex != screenStackIndex),
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    swipeableViewModel.horizontalScreenDragEnded(xBreakPoint = screenXOffset / 2)

                },
                state = rememberDraggableState { delta ->
                    swipeableViewModel.updateTopScreenXOffset(delta)
                }
            )
    )
}

/*
@Composable
fun NavigationScreen(
    swipeableViewModel: SwipeableViewModel = SwipeableViewModel(),
    context:Context
) {
    val navigationState = swipeableViewModel.navigationState.collectAsState()
    val navigationBackStack = navigationState.value.navigationBackStack
    val screenXOffsetSet = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
                //--Set screenXOffset if not set--
                if (!screenXOffsetSet.value) {
                    val rect = layoutCoordinates.boundsInRoot()
                    swipeableViewModel.setScreenXOffset(rect.topRight.x)
                    screenXOffsetSet.value = true
                }
            }
    ){
        for((index, screen) in navigationBackStack.withIndex()){
            when(screen){
                is AppScreenTypes.Screen1 -> {
                    AnimatedVisibility(visible = index >= navigationBackStack.size - 2) {
                        HomeScreen(
                            screenStackIndex = index,
                            swipeableViewModel = swipeableViewModel,
                            )
                    }
                }
                is AppScreenTypes.Screen2 -> {
                    AnimatedVisibility(visible = index >= navigationBackStack.size - 2) {
                        InfoScreen(
                            screenStackIndex = index,
                            swipeableViewModel = swipeableViewModel,
                            context = context,
                            id = 1
                            )
                    }
                }
            }
        }
    }

}*/

