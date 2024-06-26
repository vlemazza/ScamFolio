package com.dosti.scamfolio.api

import android.util.Log
import com.dosti.scamfolio.db.repositories.Repository

suspend fun initializeDataAPI(repository: Repository, onlyPrice: Boolean): Boolean {
    try {

        val newCoin = ConnectionRetrofit.callApi().getAllCoins(
            "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
            vsCurrency = "eur",
            order = "market_cap_desc",
            perPage = "250",
            sparklineBoolean = false
        )
        newCoin.find { it.autoID == 0 }?.time_fetched = System.currentTimeMillis()

        newCoin.let {
            for (coinData in it) {
                if(!onlyPrice) repository.insertCoinAPI(it)
                repository.insertCoinForBalance(coinData.id, coinData.current_price.toDouble())
                repository.updateCoinForBalance(coinData.id, coinData.current_price.toDouble())
            }
        }
        return true

    } catch (e: Exception) {
        e.message?.let { Log.d("Network API", it) }
        return false
    }
}