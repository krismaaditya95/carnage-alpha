package com.snister.carnagealpha.features.expense_tracker.data.repository

import android.content.SharedPreferences
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository

class LocalRepositoryImpl(
    private val prefs: SharedPreferences
): LocalRepository{

    companion object {
        private const val KEY_BALANCE = "KEY_BALANCE"
        private const val KEY_FIRST_TIME_SETUP = "KEY_FIRST_TIME_SETUP"
    }

    override suspend fun getBalance(): Long {
        return prefs.getLong(KEY_BALANCE, 0)
    }

    override suspend fun updateBalance(balance: Long) {
        prefs.edit().putLong(KEY_BALANCE, balance).apply()
    }

    override suspend fun hasPerformedInitialSetup(): Boolean {
        return prefs.getBoolean(KEY_FIRST_TIME_SETUP, false)
    }

    override suspend fun initialSetupDone() {
        prefs.edit().putBoolean(KEY_FIRST_TIME_SETUP, true).apply()
    }
}