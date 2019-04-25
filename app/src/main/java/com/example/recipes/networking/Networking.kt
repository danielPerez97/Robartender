package com.example.recipes.networking

import com.example.recipes.dagger.MyApp
import com.example.recipes.activities.SubmitUiModel
import com.example.recipes.model.Ingredient
import com.example.recipes.model.Recipe
import com.squareup.moshi.Moshi
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.xml.transform.Transformer

class Networking(app: MyApp)
{
    init
    {
        app.appComponent.inject(this)
    }
    @Inject lateinit var robartenderService: RobartenderService

    val getIngredients: ObservableTransformer<Any, SubmitUiModel> = ObservableTransformer{
        it.observeOn(Schedulers.io())
            .flatMap {
                robartenderService.getIngredients()
                    .map<SubmitUiModel> { SubmitUiModel.Success.GetIngredients(it) }
                    .onErrorReturn{ t -> SubmitUiModel.Failure(t) }
                    .startWith(SubmitUiModel.InTransit())
            }
    }

    val getRecipes: ObservableTransformer<Any, SubmitUiModel> = ObservableTransformer {
        it.observeOn(Schedulers.io())
            .flatMap {
                robartenderService.getRecipes()
                    .map<SubmitUiModel> { SubmitUiModel.Success.GetRecipes(it) }
                    .onErrorReturn { t -> SubmitUiModel.Failure(t) }
                    .startWith(SubmitUiModel.InTransit())
            }
    }

    val sendIngredients: ObservableTransformer<List<Ingredient>, SubmitUiModel> = ObservableTransformer {
        it.observeOn(Schedulers.io())
            .flatMapIterable { it }
            .flatMap {
                robartenderService.uploadIngredients(it.pump.toString(), it)
                    .toSingleDefault<SubmitUiModel>(SubmitUiModel.Success())
                    .onErrorReturn { return@onErrorReturn SubmitUiModel.Failure(it) }
                    .toObservable()
                    .startWith(SubmitUiModel.InTransit())
            }
            .distinctUntilChanged()
    }

    val makeRecipe: ObservableTransformer<Recipe, SubmitUiModel> = ObservableTransformer {
        it.observeOn(Schedulers.io())
            .flatMap {
                robartenderService.makeRecipe(it)
                    .toSingleDefault<SubmitUiModel>(SubmitUiModel.Success())
                    .onErrorReturn{ return@onErrorReturn SubmitUiModel.Failure(it) }
                    .toObservable()
                    .startWith(SubmitUiModel.InTransit())
            }
    }
}