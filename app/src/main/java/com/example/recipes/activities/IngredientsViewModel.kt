package com.example.recipes.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recipes.networking.Networking
import com.example.recipes.dagger.MyApp
import com.example.recipes.model.Ingredient
import com.example.recipes.model.Recipe
import io.reactivex.Observable

class IngredientsViewModel(val app: Application): AndroidViewModel(app)
{
    private val network = Networking(app as MyApp)

    fun getIngredients(): Observable<SubmitUiModel>
    {
        return Observable.just( Unit )
            .compose(network.getIngredients)
    }

    fun getRecipes(): Observable<SubmitUiModel>
    {
        return Observable.just( Unit )
            .compose(network.getRecipes)
    }

    fun sendIngredients(ingredients: List<Ingredient>): Observable<SubmitUiModel>
    {
        return Observable.just( ingredients )
            .compose(network.sendIngredients)
    }

    fun makeRecipe(recipe: Recipe): Observable<SubmitUiModel>
    {
        return Observable.just( recipe )
            .compose(network.makeRecipe)
    }
}