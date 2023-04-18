package com.learn.foodlist.presentation.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.learn.foodlist.data.repository.Repository
import com.learn.foodlist.model.FoodRecipe
import com.learn.foodlist.util.Constants.API_KEY
import com.learn.foodlist.util.Constants.DEFAULT_RECIPES_NUMBER
import com.learn.foodlist.util.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.learn.foodlist.util.Constants.QUERY_API_KEY
import com.learn.foodlist.util.Constants.QUERY_FILL_INGREDIENTS
import com.learn.foodlist.util.Constants.QUERY_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    val recipesResponse = mutableStateOf<List<FoodRecipe>>(listOf())

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)

    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        viewModelScope.launch {
                if (hasInternetConnection()) {
                    try {
                        val response = repository.getAll(queries)
                        withContext(Dispatchers.Main){
                            if (response.isSuccessful){
                                response.body()?.let {
                                    recipesResponse.value = listOf(it)
                                }
                            }
                        }

                    } catch (e: Exception) {
                        Toast.makeText(getApplication(),"Recipes not found.",Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Toast.makeText(getApplication(),"No Internet Connection.",Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }


    fun parseHtml(description: String): String{
        return Jsoup.parse(description).text()
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}