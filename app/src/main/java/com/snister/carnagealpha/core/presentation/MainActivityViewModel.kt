package com.snister.carnagealpha.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSourceLedgerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val localRepository: LocalRepository,
    private val sourceLedgerUseCase: UpsertSourceLedgerUseCase
): ViewModel() {

    var state by mutableStateOf(MainActivityState())
        private set

    private val _mainActivityEventChannel = Channel<MainActivityEvents>()
    val event = _mainActivityEventChannel.receiveAsFlow()

    init {
        insertInitialDefaultSourceLedger()
    }

    fun onAction(action: MainActivityAction){
        when (action){
//            MainActivityAction.InsertInitialDefaultSourceLedger -> TODO()
            MainActivityAction.LoadSourceLedger -> TODO()
        }
    }

    private fun insertInitialDefaultSourceLedger(){

        val ledgerDebit = SourceLedgerEntity(
            sourceLedgerId = null,
            sourceLedgerName = "My Debit Card",
            sourceLedgerBalance = 100000
        )

        val ledgerWallet = SourceLedgerEntity(
            sourceLedgerId = null,
            sourceLedgerName = "My Wallet",
            sourceLedgerBalance = 0
        )

        val ledgerSavings = SourceLedgerEntity(
            sourceLedgerId = null,
            sourceLedgerName = "My Savings",
            sourceLedgerBalance = 0
        )

        viewModelScope.launch(Dispatchers.IO){
            if(!localRepository.hasPerformedInitialSetup()){

                if(saveSourceLedger(ledgerDebit) && saveSourceLedger(ledgerWallet) && saveSourceLedger(ledgerSavings)){
                    _mainActivityEventChannel.send(MainActivityEvents.UpsertSourceLedgerSuccess)
                    localRepository.initialSetupDone()
                    localRepository.setCurrentSelectedSourceLedgerId(1)
                }else{
                    _mainActivityEventChannel.send(MainActivityEvents.UpsertSourceLedgerFailed)
                }
            }
        }
    }

    private suspend fun saveSourceLedger(sourceLedgerEntity: SourceLedgerEntity): Boolean{
        return sourceLedgerUseCase(sourceLedgerEntity)
    }


}