package com.example.recipes.model

import io.reactivex.Completable
import io.reactivex.Single
import model.Ingredient
import model.Recipe
import retrofit2.http.GET
import retrofit2.http.POST

interface RobartenderService
{
    @POST("/setingredients")
    fun uploadIngredients(ingredients: List<Ingredient>): Completable

    @POST("/setrecipes")
    fun uploadRecipes(recipes: List<Recipe>): Completable

    @GET("/ingredients")
    fun getIngredients(): Single<Ingredient>

    @GET("/recipes")
    fun getRecipes(): Single<Recipe>
}