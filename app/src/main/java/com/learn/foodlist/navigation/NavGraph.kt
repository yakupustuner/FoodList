package com.learn.foodlist.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.learn.foodlist.presentation.details.DetailsScreen
import com.learn.foodlist.presentation.home.HomeScreen
import com.learn.foodlist.presentation.welcome.WelcomeScreen
import com.learn.foodlist.util.Constants.DETAILS_ARGUMENT_KEY
import com.learn.foodlist.util.Constants.DETAILS_ARGUMENT_KEY2
import com.learn.foodlist.util.Constants.DETAILS_ARGUMENT_KEY3
import com.learn.foodlist.util.Constants.DETAILS_ARGUMENT_KEY4
import com.learn.foodlist.util.Constants.DETAILS_ARGUMENT_KEY5
import com.learn.foodlist.util.Constants.DETAILS_ARGUMENT_KEY6

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController: NavHostController,startDestination : String){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            },
                navArgument(DETAILS_ARGUMENT_KEY2){
                    type = NavType.StringType
                },
                        navArgument(DETAILS_ARGUMENT_KEY3){
                    type = NavType.StringType
                },
                        navArgument(DETAILS_ARGUMENT_KEY4){
                    type = NavType.StringType
                },
                        navArgument(DETAILS_ARGUMENT_KEY5){
                    type = NavType.IntType
                },
                        navArgument(DETAILS_ARGUMENT_KEY6){
                    type = NavType.IntType
                }
            )
        ) {
            val id =
                it.arguments?.getInt(DETAILS_ARGUMENT_KEY)
            val image =
                it.arguments?.getString(DETAILS_ARGUMENT_KEY2)
            val title =
                it.arguments?.getString(DETAILS_ARGUMENT_KEY3)
            val summary =
                it.arguments?.getString(DETAILS_ARGUMENT_KEY4)
            val readyInMinutes =
                it.arguments?.getInt(DETAILS_ARGUMENT_KEY5)
            val aggregateLikes =
                it.arguments?.getInt(DETAILS_ARGUMENT_KEY6)
            if (id != null) {
                if (image != null) {
                    if (title != null) {
                        if (summary != null) {
                            if (readyInMinutes != null) {
                                if (aggregateLikes != null) {
                                    DetailsScreen(
                                        navController = navController, recipeId = id,
                                        image = image, title = title, summary = summary,
                                        readyInMinutes = readyInMinutes, aggregateLikes = aggregateLikes
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}