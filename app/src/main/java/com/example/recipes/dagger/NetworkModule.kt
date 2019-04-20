package com.example.recipes.dagger

import com.example.recipes.networking.RobartenderService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(val baseUrl: String)
{
    @Provides
    @Singleton
    fun provideMoshi(): Moshi
    {
        return Moshi.Builder().build();
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): RobartenderService
    {
        return retrofit.create(RobartenderService::class.java)
    }
}