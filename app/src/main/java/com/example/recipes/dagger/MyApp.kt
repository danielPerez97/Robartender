package com.example.recipes.dagger

import android.app.Application


class MyApp : Application()
{
    lateinit var appComponent: AppComponent

    override fun onCreate()
    {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule("https://calm-anchorage-23554.herokuapp.com"))
            .build()
    }
}

fun Application.getComponent(): AppComponent
{
    return (this as MyApp).appComponent
}