package com.example.cryptokotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cryptokotlin.ViewModel.CoinViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptokotlin.adapters.CoinInfoAdapter
import com.example.cryptokotlin.pojos.CoinPriceInfo


class CoinListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coinlist)

        val adapter = CoinInfoAdapter(applicationContext)
        val rvCoinPriceList = findViewById<RecyclerView>(R.id.rvCoinPriceList)
        rvCoinPriceList.layoutManager = LinearLayoutManager(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener{
            @SuppressLint("SuspiciousIndentation")
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
              val intent = CoinDetailed.newIntent(applicationContext,coinPriceInfo.fromSymbol)
                startActivity(intent)
            }
        }
        rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceL.observe(this, Observer {
            adapter.coinInfoList = it
        })


    }


}
