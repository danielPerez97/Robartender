package com.example.recipes.activities.patronview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.model.Recipe
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class RecipeViewAdapter: RecyclerView.Adapter<RecipeViewAdapter.ViewHolder>()
{
    var recipes = listOf<Recipe>()
    val orders = PublishSubject.create<Recipe>()
    val disposables = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent,false)
        return ViewHolder( v )
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bindIngredient(recipes[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        private val ingredientOne: TextView = view.findViewById(R.id.ingredientOne)
        private val ozEditOne: EditText = view.findViewById(R.id.ingOneEdit)
        private val ingredientTwo: TextView = view.findViewById(R.id.ingredientTwo)
        private val ozEditTwo: EditText = view.findViewById(R.id.ingTwoEdit)
        private val orderButton: Button = view.findViewById(R.id.orderButton)

        fun bindIngredient(recipe: Recipe)
        {
            ingredientOne.text = recipe.ingredientOne.name
            ozEditOne.setText(recipe.ingredientOne.oz.toString())

            ingredientTwo.text = recipe.ingredientTwo.name
            ozEditTwo.setText(recipe.ingredientTwo.oz.toString())


            disposables += orderButton.clicks()
                .subscribe {
                    recipe.ingredientOne.oz = if(ozEditOne.text.toString() != "") ozEditOne.text.toString().toInt() else 0
                    recipe.ingredientTwo.oz = if(ozEditTwo.text.toString() != "") ozEditTwo.text.toString().toInt() else 0
                    orders.onNext(recipe)
                }
        }
    }
}