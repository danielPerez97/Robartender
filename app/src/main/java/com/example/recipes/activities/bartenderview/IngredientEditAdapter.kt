package com.example.recipes.activities.bartenderview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.model.Ingredient

class IngredientEditAdapter: RecyclerView.Adapter<IngredientEditAdapter.ViewHolder>()
{
    var ingredients = listOf<Ingredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_editor, parent,false)
        return ViewHolder( v )
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bindIngredient(ingredients[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        private val itemName: EditText = view.findViewById(R.id.itemViewName)
        private val itemOz: EditText = view.findViewById(R.id.itemViewOz)

        fun bindIngredient(ingredient: Ingredient)
        {
            itemName.setText( ingredient.name )
            itemOz.setText( ingredient.oz.toString() )
        }
    }
}