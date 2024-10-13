package com.snister.carnagealpha.core.dependency_injection

import android.content.Context
import androidx.room.Room
import com.snister.carnagealpha.features.expense_tracker.data.data_sources.local.SpendingDatabase
import com.snister.carnagealpha.features.expense_tracker.data.repository.LocalRepositoryImpl
import com.snister.carnagealpha.features.expense_tracker.data.repository.SpendingDataRepositoryImpl
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mainBindings = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SpendingDatabase::class.java,
            "spending_database_db"
        ).build()
    }

    single{
        get<SpendingDatabase>().dao
    }

    single{
        androidApplication().getSharedPreferences(
            "spending_tracker_preferences", Context.MODE_PRIVATE
        )
    }

    singleOf(::SpendingDataRepositoryImpl).bind<SpendingDataRepository>()
    singleOf(::LocalRepositoryImpl).bind<LocalRepository>()
}