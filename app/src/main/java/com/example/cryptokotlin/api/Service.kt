package com.example.cryptokotlin.api

import com.example.cryptokotlin.pojos.CoinList
import com.example.cryptokotlin.pojos.CoinPriceRaw
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("top/totalvolfull")
    fun getCoinsInfoTop(
        @Query(QUERY_API_KEY) apikey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TSYM) tsym: String = CURRENCY
    ): Single<CoinList>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_API_KEY) apikey: String = "",
        @Query(QUERY_PARAM_TSYMS) tsyms: String = CURRENCY,
        @Query(QUERY_PARAM_FSYMS) fsyms: String,
    ): Single<CoinPriceRaw>

    companion object{
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TSYM = "tsym"
        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_PARAM_TSYMS = "tsyms"
        private const val QUERY_PARAM_FSYMS = "fsyms"
        private const val CURRENCY = "USD"
    }
}