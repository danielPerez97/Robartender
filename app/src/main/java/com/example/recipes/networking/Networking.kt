package com.example.recipes.networking

import com.example.recipes.dagger.MyApp
import com.example.recipes.model.SubmitUiModel
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Networking(app: MyApp)
{
    init
    {
        app.appComponent.inject(this)
    }
    @Inject lateinit var robartenderService: RobartenderService

    val ingredients: ObservableTransformer<Any, SubmitUiModel> = ObservableTransformer{
        it.observeOn(Schedulers.io())
            .flatMap {
                robartenderService.getIngredients()
                    .map<SubmitUiModel> { SubmitUiModel.Success(it) }
                    .onErrorReturn{ t -> SubmitUiModel.Failure(t) }
                    .startWith(SubmitUiModel.InTransit())
            }
    }
}