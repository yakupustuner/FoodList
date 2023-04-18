package com.learn.foodlist.presentation.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.learn.foodlist.model.OnBoardingPage
import com.learn.foodlist.navigation.Screen
import com.learn.foodlist.ui.theme.*
import com.learn.foodlist.util.Constants.LAST_ON_BOARDING_PAGE
import com.learn.foodlist.util.Constants.ON_BOARDING_PAGE_COUNT

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second
    )

    val pagerState = rememberPagerState()

Column(
    modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.welcomeScreenBackground)
) {
    HorizontalPager(
        count =  ON_BOARDING_PAGE_COUNT,
        modifier = Modifier.weight(10f),
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { position  ->
        PagerScreen(onBoardingPage = pages[position])
        
    }
HorizontalPagerIndicator(
    modifier = Modifier
        .weight(1f)
        .align(Alignment.CenterHorizontally),
    pagerState = pagerState,
    activeColor = MaterialTheme.colors.activeIndicatorColor,
    inactiveColor = MaterialTheme.colors.inactiveIndicatorColor,
    indicatorWidth = INDICATOR_WIDTH,
    spacing = INDICATOR_SPACING
)
    FinishButton(
        modifier = Modifier.weight(1f),
        pagerState = pagerState
    ) {
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
        welcomeViewModel.saveOnBoardingState(completed = true)
    }
}
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = ""
        )

    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FinishButton(modifier: Modifier, pagerState: PagerState,  onClick: () -> Unit) {
    Row(
        modifier = modifier
            .padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == LAST_ON_BOARDING_PAGE
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.buttonBackgroundColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Finish")
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Second)
    }
}
