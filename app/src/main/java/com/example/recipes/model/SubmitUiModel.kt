package com.example.recipes.model

sealed class SubmitUiModel
{
    class Success(val ingredients: List<Ingredient>): SubmitUiModel()
    class Failure(val t: Throwable): SubmitUiModel()
    class InTransit(): SubmitUiModel()
}