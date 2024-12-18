package com.snister.carnagealpha.config

import android.app.Application
import com.snister.carnagealpha.core.dependency_injection.mainBindings
import com.snister.carnagealpha.features.expense_tracker.bindings.balanceBinding
import com.snister.carnagealpha.features.expense_tracker.bindings.dashboardOverviewBinding
import com.snister.carnagealpha.features.expense_tracker.bindings.incomeOverviewBinding
import com.snister.carnagealpha.features.expense_tracker.bindings.spendingOverviewBinding
import com.snister.carnagealpha.features.expense_tracker.bindings.upsertIncomeBinding
import com.snister.carnagealpha.features.expense_tracker.bindings.upsertSpendingBinding
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@App)
            modules(
                mainBindings,
                dashboardOverviewBinding,
                spendingOverviewBinding,
                incomeOverviewBinding,
                upsertIncomeBinding,
                upsertSpendingBinding,
//                upsertIncomeBinding
            )
        }
    }
}