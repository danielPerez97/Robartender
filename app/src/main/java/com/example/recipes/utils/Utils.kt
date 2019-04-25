@file:JvmName("Utils")

package com.example.recipes.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

fun Activity.beginActivity(start: Class<*>) {
        val intent = Intent(this, start)
        this.startActivity(intent)
    }

fun Activity.log(message: String) {
        Log.d("DEBUGGING", message) }

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT)
{
    Toast.makeText(this, message, length).show()
}
