package com.example.uiassignmentseptember

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uiassignmentseptember.compose.Gallery
import com.example.uiassignmentseptember.compose.HomeScreen
import com.example.uiassignmentseptember.compose.InfoScreen
import com.example.uiassignmentseptember.compose.Transaction
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiAssignmentSeptemberTheme {
                navController = rememberNavController()
                App(navController as NavHostController,this@MainActivity)
                //NavigationScreen(context = this@MainActivity)
            }
        }
    }
}

@Composable
fun App(navController: NavHostController,context: Context) {
    val animationTime = 1200
    NavHost(
        navController = navController,
        startDestination = "Gallery"
    ) {
        composable(
            route = "Home",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animationTime)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animationTime)
                )
            }
        ) {
            HomeScreen(
                navController = navController,
            )
        }

        composable(
            route = "InfoScreen/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animationTime)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animationTime)
                )
            }
        ) {
            InfoScreen(
                it.arguments!!.getInt("id"),
                context,
                navController
                )
        }

        composable(
            route = "transaction/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(animationTime)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(animationTime)
                )
            }
        ) {
            it.arguments?.let { it1 ->
                Transaction(
                    textFont = FontFamily(Font(R.font.impact)),
                    context = context, modelId = it1.getInt("id"))
            }
        }

        composable(route = "Gallery") {
            Gallery(context = context)
        }
    }
}

