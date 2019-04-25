package com.example.recipes.activities

import com.example.recipes.model.Ingredient
import com.example.recipes.model.Recipe

sealed class SubmitUiModel
{
    open class Success : SubmitUiModel()
    {
        class GetIngredients(val ingredients: List<Ingredient>): Success()
        class GetRecipes(val recipes: List<Recipe>): Success()
    }
    class Failure(val t: Throwable): SubmitUiModel()
    class InTransit: SubmitUiModel()
}