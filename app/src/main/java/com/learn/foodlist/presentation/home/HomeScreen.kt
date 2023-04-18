package com.learn.foodlist.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.learn.foodlist.navigation.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.learn.foodlist.R
import com.learn.foodlist.model.Result
import com.learn.foodlist.presentation.components.ShimmerEffect
import com.learn.foodlist.ui.theme.*
import com.learn.foodlist.util.Constants.RECIPE_TEXT_MAX_LINES
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.statusBarColor
    var isLoading by remember{
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = true){
        delay(3000)
        isLoading = false
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }

    Scaffold(
        topBar = {
            HomeTopBar(
            )
        },
        content = {
              LazyColumn(
                  contentPadding = PaddingValues(all = SMALL_PADDING),
                  verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
              ){
                  homeViewModel.getRecipes(homeViewModel.applyQueries())
                  homeViewModel.recipesResponse.value.let { foodRecipe ->
                      foodRecipe.map {
                        val ref =  it.results
                          items(items = ref){ list ->
                              ShimmerEffect(
                                  isLoading = isLoading,
                                  contentAfterLoading = {
                                      RecipesItems(list = list, navController = navController)
                                  },
                                  )
                          }
                      }
                          }
                      }
        }
    )
}

@Composable
fun RecipesItems(list: Result, navController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier
            .height(RECIPE_ITEM_HEIGHT)
            .clickable {
                navController.navigate(
                    Screen.Details.passId(
                        recipeId = list.recipeId, image = list.image,
                        title = list.title, summary = list.summary,
                        readyInMinutes = list.readyInMinutes,
                        aggregateLikes = list.aggregateLikes
                    )
                )
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
           Image(painter = rememberAsyncImagePainter(list.image),
                modifier = Modifier.fillMaxSize(),
                contentDescription = stringResource(id = R.string.recipe_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = list.title,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    homeViewModel.parseHtml(list.summary),
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = RECIPE_TEXT_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}



