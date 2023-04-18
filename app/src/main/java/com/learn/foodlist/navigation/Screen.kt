package com.learn.foodlist.navigation

sealed class Screen(val route:String){
    object Welcome : Screen("welcome_screen")
    object Home : Screen("home_screen")
    object Details :
        Screen(
            "details_screen?recipeId={recipeId}&image={image}&title={title}&summary={summary}&readyInMinutes={readyInMinutes}&aggregateLikes={aggregateLikes}") {
        fun passId(
            recipeId:Int,image:String,
            title:String,summary:String,
            readyInMinutes:Int,aggregateLikes:Int
        ): String {
            return "details_screen?recipeId=$recipeId&image=$image&title=$title&summary=$summary&readyInMinutes=$readyInMinutes&aggregateLikes=$aggregateLikes"
        }
    }
}
