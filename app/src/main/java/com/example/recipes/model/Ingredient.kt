package com.example.recipes.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredient(@Json(name = "ingredient") val name: String, val oz: Int, val pump: Int)