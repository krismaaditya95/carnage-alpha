package com.snister.carnagealpha.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSourceLedgerUseCase
import com.snister.carnagealpha.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val localRepository: LocalRepository,
    private val sourceLedgerUseCase: UpsertSourceLedgerUseCase,
    private val sourceLedgerRepository: SourceLedgerRepository,
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
            MainActivityAction.ShowChangeSourceLedgerDialog -> showChangeSourceLedgerDialog()
            MainActivityAction.HideChangeSourceLedgerDialog -> hideChangeSourceLedgerDialog()
            is MainActivityAction.OnSourceLedgerItemSelected -> onSourceLedgerSelected(
                action.selectedSourceLedgerIndex,
                action.selectedSourceLedgerId
            )
            MainActivityAction.OnSaveSelectedSourceLedger -> onSaveSelectedSourceLedger()
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

    private fun showChangeSourceLedgerDialog(){
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                isLoading = true
            )
            state = state.copy(
                isChangeSourceLedgerDialogVisible = true,
                // sourceLedgerList
                sourceLedgerList = sourceLedgerRepository.getAllSourceLedger(),
                currentActiveSourceLedgerId = localRepository.getCurrentSelectedSourceLedgerId()
            )
            state = state.copy(
                isLoading = false,
            )
        }
    }

    private fun hideChangeSourceLedgerDialog(){
        viewModelScope.launch {
            state = state.copy(
                isChangeSourceLedgerDialogVisible = false,
                selectedSourceLedgerIdFromList = 0,
                selectedSourceLedgerIndexFromList = 0
            )
        }
    }

    private fun onSourceLedgerSelected(index: Int, id: Int){
        viewModelScope.launch {
            state = state.copy(
                selectedSourceLedgerIndexFromList = index,
                selectedSourceLedgerIdFromList = id,
            )
        }
    }

    private fun onSaveSelectedSourceLedger(){
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.setCurrentSelectedSourceLedgerId(
                state.selectedSourceLedgerIdFromList
            )
        }
    }

}