package com.example.cryptokotlin.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptokotlin.api.ApiFactory
import com.example.cryptokotlin.db.AppData
import com.example.cryptokotlin.pojos.CoinPriceInfo
import com.example.cryptokotlin.pojos.CoinPriceRaw
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppData.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val priceL = db.CoinPriceInfoDAO().getPriceList()


    init {
        dataload()
    }

    fun getDetail(fsym:String): LiveData<CoinPriceInfo> {
        return db.CoinPriceInfoDAO().getCoinInfo(fsym)

    }
    private fun dataload() {
        val disposable = ApiFactory.apiService.getCoinsInfoTop(limit = 50)
            .map{it.data?.map { it.coinInfo?.name }?.joinToString(",")}
            .flatMap { ApiFactory.apiService.getFullPriceList(fsyms = it) }
            .map{getPriceListFromRAW(it)}
            .delaySubscription(20,TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null) {
                    db.CoinPriceInfoDAO().insertPriceList(it)
                    Log.d("Load_data_Test", it.toString())
                }
            }, {
                Log.d("Load_data_Test_Exc", it.toString())
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRAW(coinPriceRaw: CoinPriceRaw): List<CoinPriceInfo>? {
        val json = coinPriceRaw.coinPriceInfoJsonObject
        val res = ArrayList<CoinPriceInfo>()
        if (json == null) return null
        val coinKeyset = json.keySet()
        for (coinKey in coinKeyset){
            val currencyJson = json.getAsJsonObject(coinKey)
            val currencyKeyset = currencyJson.keySet()
            for (currencyKey in currencyKeyset){
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                res.add(priceInfo)
            }

        }
        return res
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}