package com.example.recipes.activities

import com.example.recipes.model.Ingredient

sealed class SubmitUiModel
{
    open class Success : SubmitUiModel()
    {
        class GetIngredients(val ingredients: List<Ingredient>): Success()
    }
    class Failure(val t: Throwable): SubmitUiModel()
    class InTransit: SubmitUiModel()
}