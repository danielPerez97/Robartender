package com.example.recipes.networking

import com.example.recipes.model.Ingredient
import com.example.recipes.model.Recipe
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface RobartenderService
{
    @PUT("/pump/{pumpId}")
    fun uploadIngredients(@Path("pumpId") pumpId: Int, @Body ingredient: String): Completable

    @POST("/setrecipes")
    fun uploadRecipes(recipes: List<Recipe>): Completable

    @GET("/getIngredients")
    fun getIngredients(): Observable<List<Ingredient>>

    @GET("/recipes")
    fun getRecipes(): Observable<Recipe>
}