package com.example.recipes.dagger

import android.app.Application
import dagger.Module
import javax.inject.Singleton
import dagger.Provides


@Module
class AppModule(var mApplication: Application) {

    @Provides
    @Singleton
    internal fun providesApplication(): Application
    {
        return mApplication
    }
}