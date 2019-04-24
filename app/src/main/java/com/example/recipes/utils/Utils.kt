@file:JvmName("Utils")

package com.example.recipes.utils

import android.app.Activity
import android.content.Intent
import android.util.Log

fun Activity.beginActivity(start: Class<*>) {
        val intent = Intent(this, start)
        this.startActivity(intent)
    }

fun Activity.log(message: String) {
        Log.d("DEBUGGING", message) }
