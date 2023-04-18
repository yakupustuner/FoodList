package com.learn.foodlist.presentation.home


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.learn.foodlist.ui.theme.appBarBackgroundColor
import com.learn.foodlist.ui.theme.appBarContentColor

@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Food List",
                color = MaterialTheme.colors.appBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.appBarBackgroundColor,

    )
}
