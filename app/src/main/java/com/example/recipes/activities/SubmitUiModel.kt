package com.example.recipes.activities

import com.example.recipes.model.Ingredient

sealed class SubmitUiModel
{
    class Success(val ingredients: List<Ingredient>): SubmitUiModel()
    class Failure(val t: Throwable): SubmitUiModel()
    class InTransit: SubmitUiModel()
}