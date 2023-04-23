package com.example.cryptokotlin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptokotlin.pojos.CoinPriceInfo

@Dao
interface CoinPriceInfoDAO {
    @Query("Select * From price_list Order by lastupdate DESC")
    fun getPriceList():LiveData<List<CoinPriceInfo>>

    @Query("Select * from price_list where fromSymbol == :fsym LIMIT 1")
    fun getCoinInfo(fsym:String):LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<CoinPriceInfo>)
}