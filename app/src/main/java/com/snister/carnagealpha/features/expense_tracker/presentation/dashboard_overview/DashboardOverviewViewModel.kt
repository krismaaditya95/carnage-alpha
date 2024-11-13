package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.GetAllSourceLedgerUseCase
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.GetSourceLedgerByIdUseCase
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.GetSpendingByDateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class DashboardOverviewViewModel(
//    private val spendingDataRepository: SpendingDataRepository,
    private val localRepository: LocalRepository,
    private val getAllSourceLedgerUseCase: GetAllSourceLedgerUseCase,
    private val getSourceLedgerByIdUseCase: GetSourceLedgerByIdUseCase,
    private val getSpendingByDateUseCase: GetSpendingByDateUseCase
) : ViewModel(){

    var state by mutableStateOf(DashboardOverviewState())
        private set

    fun onAction(action: DashboardOverviewAction){
        when (action){

            DashboardOverviewAction.LoadSpendingOverviewAndBalance -> loadSpendingListAndBalance()
            is DashboardOverviewAction.OnDateChange -> TODO()
            is DashboardOverviewAction.OnDeleteSpending -> TODO()
            DashboardOverviewAction.LoadSourceLedgerList -> loadSourceLedgerList()
            DashboardOverviewAction.LoadSourceLedgerById -> loadSourceLedgerById()
        }
    }

    private fun loadSourceLedgerList(){
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                sourceLedgerList = getAllSourceLedgerUseCase()
            )
        }
    }

    private fun loadSourceLedgerById(){
        viewModelScope.launch(Dispatchers.IO) {

            state = state.copy(
                currentSourceLedger = getSourceLedgerByIdUseCase(
                    localRepository.getCurrentSelectedSourceLedgerId()
                )
            )
        }
    }

    private fun loadSpendingListAndBalance(){
        viewModelScope.launch(Dispatchers.IO) {
//            val allDates = spendingDataRepository.getAllDates()

            state = state.copy(
                currentActiveSourceLedgerId = localRepository.getCurrentSelectedSourceLedgerId()
            )

            state = state.copy(
                //note: spendingList belum dipake dimanapun
                spendingList = getSpendingListByDate(
                    ZonedDateTime.now(),
                    state.currentActiveSourceLedgerId
                ),

                //note: balance localrepository dipake sebelumnya di MinimizedBalanceCard
                //yang ada di dashboard, ini akan diganti dengan SourceLedger.balance nantinya
                balance = localRepository.getBalance(),
                // -> balance diganti dengan currentSourceLedger
                currentSourceLedger = getSourceLedgerByIdUseCase(
                    state.currentActiveSourceLedgerId
                ),
                // ini sourceLedgerList. optinal untuk ngetest
                sourceLedgerList = getAllSourceLedgerUseCase(),

                //note: pickedDate belum dipake dimanapun
                //pickedDate = allDates.lastOrNull() ?: ZonedDateTime.now(),

                //note: datesList belum dipake dimanapun
                //datesList = allDates.reversed()
            )
        }
    }

    private suspend fun getSpendingListByDate(
        date: ZonedDateTime,
        sourceLedgerId: Int
    ): List<SpendingEntity>{
        return getSpendingByDateUseCase(date, sourceLedgerId)
    }
}