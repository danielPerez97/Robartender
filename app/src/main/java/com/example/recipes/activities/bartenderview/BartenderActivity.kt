package com.example.recipes.activities.bartenderview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.recipes.R
import com.example.recipes.activities.IngredientsViewModel
import com.example.recipes.activities.SubmitUiModel
import com.example.recipes.dagger.getComponent
import com.example.recipes.model.Ingredient
import com.example.recipes.utils.toast
import com.jakewharton.rxbinding3.view.clicks
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BartenderActivity: AppCompatActivity()
{
    @Inject lateinit var moshi: Moshi
    @BindView(R.id.barRecycView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.barRefreshBtn) lateinit var refreshButton: Button
    @BindView(R.id.barSubmitBtn) lateinit var submitButton: Button
    @BindView(R.id.barProgSpinner) lateinit var spinner: ProgressBar
    private lateinit var viewModel: IngredientsViewModel
    private val ingAdapter = IngredientEditAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        application.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bartender)
        ButterKnife.bind(this)

        val lm = LinearLayoutManager(recyclerView.context)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, lm.orientation)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(dividerItemDecoration)
            adapter = ingAdapter
        }
        viewModel = ViewModelProviders.of(this).get(IngredientsViewModel::class.java)

        disposables += Observable.merge( Observable.just(Unit), refreshButton.clicks() )
            .debounce( 300, TimeUnit.MILLISECONDS )
            .switchMap { viewModel.getIngredients() }
            .distinctUntilChanged()
            .observeOn( AndroidSchedulers.mainThread() )
            .subscribe { uiNotification: SubmitUiModel ->
                when(uiNotification)
                {
                    is SubmitUiModel.InTransit -> startSpinner()
                    is SubmitUiModel.Failure ->
                    {
                        showFailure( uiNotification.t )
                        Log.wtf("DEBUGGING", uiNotification.t.localizedMessage)
                    }
                    is SubmitUiModel.Success.GetIngredients -> updateIngredients( uiNotification.ingredients )
                }
            }

        disposables += submitButton.clicks()
            .map { ingAdapter.ingredients }
            .debounce(300, TimeUnit.MILLISECONDS)
            .switchMap { viewModel.sendIngredients(it) }
            .observeOn( AndroidSchedulers.mainThread() )
            .subscribe { uiNotification: SubmitUiModel ->
                when(uiNotification)
                {
                    is SubmitUiModel.InTransit -> startSpinner()
                    is SubmitUiModel.Failure ->  showFailure(uiNotification.t)
                    is SubmitUiModel.Success ->  uploadSuccess()
                }
            }
    }

    private fun startSpinner()
    {
        spinner.visibility = View.VISIBLE
    }

    private fun showFailure(t: Throwable)
    {
        spinner.visibility = View.INVISIBLE
        Toast.makeText(this, t.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun updateIngredients(ingredients: List<Ingredient>)
    {
        ingAdapter.ingredients = ingredients
        ingAdapter.notifyDataSetChanged()
        spinner.visibility = View.INVISIBLE
    }

    private fun uploadSuccess()
    {
        spinner.visibility = View.INVISIBLE
        toast("Success")
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        ingAdapter.disposables.dispose()
    }
}
