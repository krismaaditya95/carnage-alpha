package com.snister.carnagealpha.core.dependency_injection

import android.content.Context
import androidx.room.Room
import com.snister.carnagealpha.core.presentation.MainActivityViewModel
import com.snister.carnagealpha.features.expense_tracker.data.data_sources.local.IncomeDatabase
import com.snister.carnagealpha.features.expense_tracker.data.data_sources.local.SourceLedgerDatabase
import com.snister.carnagealpha.features.expense_tracker.data.data_sources.local.SpendingDatabase
import com.snister.carnagealpha.features.expense_tracker.data.repository.LocalRepositoryImpl
import com.snister.carnagealpha.features.expense_tracker.data.repository.SpendingDataRepositoryImpl
import com.snister.carnagealpha.features.expense_tracker.data.repository.IncomeDataRepositoryImpl
import com.snister.carnagealpha.features.expense_tracker.data.repository.SourceLedgerRepositoryImpl
import com.snister.carnagealpha.features.expense_tracker.domain.repository.IncomeDataRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSourceLedgerUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val mainBindings = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SourceLedgerDatabase::class.java,
            "source_ledger_database_db"
        ).build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            SpendingDatabase::class.java,
            "spending_database_db"
        ).build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            IncomeDatabase::class.java,
            "income_database_db"
        ).build()
    }

    single {
        get<SourceLedgerDatabase>().sourceLedgerDao
    }

    single{
        get<SpendingDatabase>().dao
    }

    single{
        get<IncomeDatabase>().dao
    }

    single{
        androidApplication().getSharedPreferences(
            "spending_tracker_preferences", Context.MODE_PRIVATE
        )
    }

    singleOf(::SpendingDataRepositoryImpl).bind<SpendingDataRepository>()
    singleOf(::IncomeDataRepositoryImpl).bind<IncomeDataRepository>()
    singleOf(::LocalRepositoryImpl).bind<LocalRepository>()
    singleOf(::SourceLedgerRepositoryImpl).bind<SourceLedgerRepository>()

    single {
        UpsertSourceLedgerUseCase(get())
    }
    viewModel {
        MainActivityViewModel(get(), get())
    }
}