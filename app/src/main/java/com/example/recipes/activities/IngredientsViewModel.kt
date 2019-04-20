package com.example.recipes.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recipes.networking.Networking
import com.example.recipes.dagger.MyApp
import com.example.recipes.model.SubmitUiModel
import io.reactivex.Observable

class IngredientsViewModel(val app: Application): AndroidViewModel(app)
{
    private val network = Networking(app as MyApp)

    fun getIngredients(): Observable<SubmitUiModel>
    {
        return Observable.just(1)
            .compose(network.ingredients)
    }
}