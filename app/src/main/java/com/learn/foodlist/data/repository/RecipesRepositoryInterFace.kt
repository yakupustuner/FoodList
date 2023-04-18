package com.learn.foodlist.data.repository

import com.learn.foodlist.model.FoodRecipe
import retrofit2.Response

interface RecipesRepositoryInterFace {
    suspend fun getAll(searchQuery: Map<String, String>): Response<FoodRecipe>
}