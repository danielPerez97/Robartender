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
            .networkModule(NetworkModule("http://localhost:3000"))
            .build()
    }
}

fun Application.getComponent(): AppComponent
{
    return (this as MyApp).appComponent
}