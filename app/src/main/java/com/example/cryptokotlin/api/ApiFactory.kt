package com.example.cryptokotlin.api


import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val baseUrl = "https://min-api.cryptocompare.com/data/"
    const val baseImgUrl = "https://cryptocompare.com"
    private val  retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .build()

    val apiService = retrofit.create(Service::class.java)
}