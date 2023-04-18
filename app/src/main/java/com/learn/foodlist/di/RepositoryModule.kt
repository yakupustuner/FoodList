package com.learn.foodlist.di

import com.learn.foodlist.data.network.FoodApi
import com.learn.foodlist.data.repository.DataOperationsImpl
import com.learn.foodlist.data.repository.DataStoreOperations
import com.learn.foodlist.data.repository.RecipesRepositoryInterFace
import com.learn.foodlist.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun injectRepository(
        api: FoodApi,
        dataStore: DataStoreOperations
    ) = Repository(api, dataStore as DataOperationsImpl) as RecipesRepositoryInterFace
}