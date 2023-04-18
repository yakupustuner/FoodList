package com.learn.foodlist.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material.Colors

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)


val Colors.statusBarColor
    get() = if (isLight) Purple700 else Color.Black
val Colors.titleColor
    get() = if (isLight) DarkGray else LightGray
val Colors.welcomeScreenBackground
    get() = if (isLight) Color.White else Color.Black

val Colors.activeIndicatorColor
get() = if (isLight) Purple500 else Purple700

val Colors.inactiveIndicatorColor
    get() = if (isLight) LightGray else DarkGray
val Colors.buttonBackgroundColor
    get() = if (isLight) Purple500 else Purple700
val Colors.appBarContentColor: Color
    get() = if (isLight) Color.White else LightGray
val Colors.appBarBackgroundColor: Color
    get() = if (isLight) Purple500 else Color.Black
val Colors.topAppBarContentColor: Color
    get() = if (isLight) Color.White else LightGray
