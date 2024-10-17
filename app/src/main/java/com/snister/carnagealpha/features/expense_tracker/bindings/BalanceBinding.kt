package com.snister.carnagealpha.features.expense_tracker.bindings

import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance.UpsertBalanceViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val balanceBinding = module {
    viewModel { UpsertBalanceViewModel(get()) }
}