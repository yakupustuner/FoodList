package com.learn.foodlist.data.repository

import com.learn.foodlist.data.network.FoodApi
import com.learn.foodlist.model.FoodRecipe
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val foodApi: FoodApi,
    private val dataStore:DataOperationsImpl
):RecipesRepositoryInterFace,DataStoreOperations{


    override suspend fun getAll(searchQuery: Map<String, String>): Response<FoodRecipe> {
       return foodApi.getRecipes(searchQuery)
    }

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    override fun readOnBoardingState(): Flow<Boolean> {
       return dataStore.readOnBoardingState()
    }


}