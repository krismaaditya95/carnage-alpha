package com.snister.carnagealpha.features.expense_tracker.bindings

import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewViewModel
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val spendingOverviewBinding = module {
    viewModel {
        SpendingOverviewViewModel(get(), get())
    }
}