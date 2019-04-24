package com.example.recipes.dagger

import com.example.recipes.MainActivity
import com.example.recipes.networking.Networking
import com.example.recipes.activities.bartenderview.BartenderActivity
import com.example.recipes.activities.patronview.PatronActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent
{
    fun inject(mainActivity: MainActivity)
    fun inject(bartenderActivity: BartenderActivity)
    fun inject(patronActivity: PatronActivity)
    fun inject(networking: Networking)
}