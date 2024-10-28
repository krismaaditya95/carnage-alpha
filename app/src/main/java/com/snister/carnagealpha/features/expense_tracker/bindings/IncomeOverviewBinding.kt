package com.snister.carnagealpha.features.expense_tracker.bindings

import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val incomeOverviewBinding = module {
    viewModel {
        IncomeOverviewViewModel(get(), get())
    }
}