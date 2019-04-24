package com.example.recipes.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredient(@Json(name = "ingredient") var name: String, var oz: Int, val pump: Int)