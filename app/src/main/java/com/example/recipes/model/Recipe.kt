package com.example.recipes.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(val id: Int,
                  val ingredientOne: Ingredient,
                  val ingredientTwo: Ingredient
)