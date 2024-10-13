package com.snister.carnagealpha.features.expense_tracker.data.repository

import android.content.SharedPreferences
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository

class LocalRepositoryImpl(
    private val prefs: SharedPreferences
): LocalRepository{

    companion object {
        private const val KEY_BALANCE = "KEY_BALANCE"
    }

    override suspend fun getBalance(): Double {
        return prefs.getFloat(KEY_BALANCE, 0f).toDouble()
    }

    override suspend fun updateBalance(balance: Double) {
        prefs.edit().putFloat(KEY_BALANCE, balance.toFloat()).apply()
    }
}