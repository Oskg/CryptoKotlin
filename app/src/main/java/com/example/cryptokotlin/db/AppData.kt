package com.example.cryptokotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptokotlin.pojos.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppData : RoomDatabase() {
    companion object{
        private var db: AppData? = null
        private const val DB_NAME = "curr.db"
        private var LOCK = Any()
        fun getInstance(context: Context) : AppData{
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppData::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }
    abstract fun CoinPriceInfoDAO(): CoinPriceInfoDAO
}