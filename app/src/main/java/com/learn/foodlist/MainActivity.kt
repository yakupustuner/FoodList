package com.learn.foodlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.learn.foodlist.data.repository.Repository
import com.learn.foodlist.navigation.Screen
import com.learn.foodlist.navigation.SetupNavGraph
import com.learn.foodlist.ui.theme.FoodListTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @Inject
    lateinit var repository: Repository
    private var completed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodListTheme {
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    startDestination = if (completed) Screen.Home.route else Screen.Welcome.route
                )
            }
            }
        lifecycleScope.launch(Dispatchers.IO) {
            repository.readOnBoardingState().collect {
                completed = it
            }
        }
                }
            }





