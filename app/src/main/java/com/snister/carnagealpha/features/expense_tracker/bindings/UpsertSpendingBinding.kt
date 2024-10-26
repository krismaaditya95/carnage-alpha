package com.snister.carnagealpha.features.expense_tracker.bindings

import androidx.lifecycle.viewmodel.compose.viewModel
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSpendingUseCase
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending.UpsertSpendingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val upsertSpendingBinding = module {
    single { UpsertSpendingUseCase(get())}
    viewModel { UpsertSpendingViewModel(get() , get()) }
}