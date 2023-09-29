package com.example.uiassignmentseptember

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uiassignmentseptember.compose.HomeScreen
import com.example.uiassignmentseptember.compose.InfoScreen
import com.example.uiassignmentseptember.compose.PhotoDetail
import com.example.uiassignmentseptember.compose.SettingScreen
import com.example.uiassignmentseptember.compose.Swap
import com.example.uiassignmentseptember.compose.Transaction
import com.example.uiassignmentseptember.ui.theme.UiAssignmentSeptemberTheme
import android.Manifest
import com.example.uiassignmentseptember.compose.QrCodeScanner

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiAssignmentSeptemberTheme {
                navController = rememberNavController()
                App(navController as NavHostController,this@MainActivity)
            }
        }
    }
}

@Composable
fun App(navController: NavHostController,context: Context) {
    val animationTime = 1200
    NavHost(
        navController = navController,
        startDestination = "Home"
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

        composable(
            route = "Swap/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) {
            if (it.arguments!=null) {
                Swap(
                    images = getImageIds(context),
                    modelId = it.arguments!!.getInt("id"),
                    textFont = FontFamily(Font(R.font.impact)),
                    navController = navController
                )
            }
        }

        composable(
            route = "PhotoDetail/{userId}/{photoId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.IntType
                },
                navArgument(name = "photoId") {
                    type = NavType.IntType
                }
            )
        ) {
            if (it.arguments!=null) {
                PhotoDetail(
                    modelId = it.arguments!!.getInt("userId"),
                    photoId = it.arguments!!.getInt("photoId"),
                    navController = navController
                )
            }
        }

        composable(route = "Setting") {
            SettingScreen(navController = navController)
        }

        composable(route = "QR Code") {
            QrCodeScanner(context = context)
        }
    }
}

