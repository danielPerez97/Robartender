package com.example.recipes.activities.bartenderview

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.activities.IngredientsViewModel
import com.example.recipes.dagger.getComponent
import com.example.recipes.model.Ingredient
import com.example.recipes.model.SubmitUiModel
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.TimeUnit

class BartenderActivity : AppCompatActivity()
{
    private lateinit var viewModel: IngredientsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshButton: Button
    private lateinit var submitButton: Button
    private lateinit var spinner: ProgressBar
    private val ingAdapter = IngredientEditAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        application.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bartender)
        recyclerView = findViewById(R.id.barRecycView)
        val lm = LinearLayoutManager(recyclerView.context)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, lm.orientation)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(dividerItemDecoration)
            adapter = ingAdapter
        }
        refreshButton = findViewById(R.id.barRefreshBtn)
        submitButton = findViewById(R.id.barSubmitBtn)
        spinner = findViewById(R.id.barProgSpinner)
        viewModel = ViewModelProviders.of(this).get(IngredientsViewModel::class.java)

        disposables += submitButton.clicks()
            .subscribe{
                updateIngredients(listOf(Ingredient("Orange Juice", 20), Ingredient("Vodka", 20), Ingredient("Water", 20)))
            }

        disposables += Observable.merge(Observable.just(Unit), refreshButton.clicks())
            .debounce(300, TimeUnit.MILLISECONDS)
            .switchMap { viewModel.getIngredients() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { uiNotification: SubmitUiModel ->
                when(uiNotification)
                {
                    is SubmitUiModel.InTransit -> startSpinner()
                    is SubmitUiModel.Failure -> showFailure(uiNotification.t)
                    is SubmitUiModel.Success -> updateIngredients(uiNotification.ingredients)
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

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}