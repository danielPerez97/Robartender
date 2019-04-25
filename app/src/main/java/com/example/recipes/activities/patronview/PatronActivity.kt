package com.example.recipes.activities.patronview

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
import butterknife.BindView
import butterknife.ButterKnife
import com.example.recipes.R
import com.example.recipes.activities.IngredientsViewModel
import com.example.recipes.activities.SubmitUiModel
import com.example.recipes.dagger.getComponent
import com.example.recipes.model.Recipe
import com.example.recipes.utils.toast
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.TimeUnit


class PatronActivity : AppCompatActivity()
{
    @BindView(R.id.patronRecycView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.patronRefreshBtn) lateinit var refreshButton: Button
    @BindView(R.id.patronProgSpinner) lateinit var spinner: ProgressBar
    private lateinit var viewModel: IngredientsViewModel
    private val recipeAdapter = RecipeViewAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        application.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patron)
        ButterKnife.bind(this)

        val lm = LinearLayoutManager( recyclerView.context )
        val dividerItemDecoration = DividerItemDecoration( recyclerView.context, lm.orientation )
        recyclerView.apply {
            layoutManager = LinearLayoutManager( context )
            addItemDecoration( dividerItemDecoration )
            adapter = recipeAdapter
        }
        viewModel = ViewModelProviders.of(this).get(IngredientsViewModel::class.java)

        disposables += Observable.merge( Observable.just(Unit), refreshButton.clicks() )
            .debounce( 300, TimeUnit.MILLISECONDS )
            .switchMap { viewModel.getRecipes() }
            .distinctUntilChanged()
            .observeOn( AndroidSchedulers.mainThread() )
            .subscribe { uiNotification: SubmitUiModel ->
                when(uiNotification) {
                    is SubmitUiModel.InTransit -> startSpinner()
                    is SubmitUiModel.Failure -> showFailure( uiNotification.t )
                    is SubmitUiModel.Success.GetRecipes -> updateRecipes( uiNotification.recipes )
                }
            }

        disposables += recipeAdapter.orders
            .switchMap { viewModel.makeRecipe(it) }
            .observeOn( AndroidSchedulers.mainThread() )
            .subscribe{ uiNotification: SubmitUiModel ->
                when(uiNotification)
                {
                    is SubmitUiModel.InTransit -> startSpinner()
                    is SubmitUiModel.Failure -> showFailure( uiNotification.t )
                    is SubmitUiModel.Success ->
                    {
                        spinner.visibility = View.INVISIBLE
                        toast("Success")
                    }
                }
            }

    }

    private fun startSpinner()
    {
        spinner.visibility = View.VISIBLE
    }

    private fun showFailure( t: Throwable )
    {
        spinner.visibility = View.INVISIBLE
        Toast.makeText(this, t.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun updateRecipes(recipes: List<Recipe> )
    {
        recipeAdapter.recipes = recipes
        recipeAdapter.notifyDataSetChanged()
        spinner.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}