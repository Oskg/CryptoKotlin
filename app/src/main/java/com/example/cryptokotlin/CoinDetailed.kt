package com.example.cryptokotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cryptokotlin.ViewModel.CoinViewModel
import com.example.cryptokotlin.databinding.ActivityCoinDetailedBinding

class CoinDetailed : AppCompatActivity() {
    private lateinit var viewModel:CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!intent.hasExtra(EXTRA_FROM_SYM)){
            finish()
            return
        }
        val fromSym = intent.getStringExtra(EXTRA_FROM_SYM)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        if (fromSym != null) {
            viewModel.getDetail(fromSym).observe(this, Observer {
                val tvPrice = binding.tvPrice
                val tvMinPrice = binding.tvMinPrice
                val tvMaxPrice = binding.tvMaxPrice
                val tvLastMarket = binding.tvLastMarket
                val tvLastUpdate = binding.tvLastUpdate
                val tvFromSymbol = binding.tvFromSymbol
                val tvToSymbol = binding.tvToSymbol
                val ivLogoCoin = binding.ivLogoCoin
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.getFormattedTime()
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Glide.with(applicationContext).load(it.getImgUrl()).into(ivLogoCoin)


            })
        }
    }
    companion object{
        private val EXTRA_FROM_SYM = "fsym"
        fun newIntent(context:Context,fromSym:String): Intent {
            val intent = Intent(context,CoinDetailed::class.java)
            intent.putExtra(EXTRA_FROM_SYM,fromSym)
            return intent
        }
    }
}