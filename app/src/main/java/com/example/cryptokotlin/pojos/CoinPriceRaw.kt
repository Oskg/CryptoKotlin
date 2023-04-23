package com.example.cryptokotlin.pojos

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class CoinPriceRaw (
@SerializedName("RAW")
@Expose
val coinPriceInfoJsonObject: JsonObject? = null
)