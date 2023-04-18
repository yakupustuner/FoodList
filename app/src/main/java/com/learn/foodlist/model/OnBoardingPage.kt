package com.learn.foodlist.model

import androidx.annotation.DrawableRes
import com.learn.foodlist.R

sealed class OnBoardingPage(
    @DrawableRes
    val image:Int,

){
    object First : OnBoardingPage(
        image = R.drawable.welcome
    )

    object Second : OnBoardingPage(
        image = R.drawable.ready
    )


}
