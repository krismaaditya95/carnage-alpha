package com.snister.carnagealpha.features.expense_tracker.bindings

import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dashboardOverviewBinding = module {
    viewModel {
        DashboardOverviewViewModel(get(), get())
    }
}