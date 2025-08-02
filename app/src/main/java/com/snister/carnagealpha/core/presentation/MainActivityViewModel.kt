package com.snister.carnagealpha.core.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.GetAllSourceLedgerUseCase
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSourceLedgerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val localRepository: LocalRepository,
    private val sourceLedgerUseCase: UpsertSourceLedgerUseCase,
    private val getAllSourceLedgerUseCase: GetAllSourceLedgerUseCase
): ViewModel() {

    var state by mutableStateOf(MainActivityState())
        private set

    private val _mainActivityEventChannel = Channel<MainActivityEvents>()
    val event = _mainActivityEventChannel.receiveAsFlow()

    init {
//        askForNotificationPermission()
        insertInitialDefaultSourceLedger()
    }

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog(){
        visiblePermissionDialogQueue.removeLast()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if(!isGranted){
            visiblePermissionDialogQueue.add(0,permission)
        }
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun arePermissionGranted(activity: Activity): Boolean{
        return ContextCompat.checkSelfPermission(
            activity.applicationContext,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForNotificationPermission(){
        viewModelScope.launch(Dispatchers.IO) {
//            _mainActivityEventChannel.send(MainActivityEvents.AskForNotificationPermission)
        }
    }

    private fun insertInitialDefaultSourceLedger(){

        val ledgerDebit = SourceLedgerEntity(
            sourceLedgerId = null,
            sourceLedgerName = "Debit Card",
            sourceLedgerBalance = 0
        )

        val ledgerWallet = SourceLedgerEntity(
            sourceLedgerId = null,
            sourceLedgerName = "Wallet",
            sourceLedgerBalance = 0
        )

        val ledgerSavings = SourceLedgerEntity(
            sourceLedgerId = null,
            sourceLedgerName = "Savings",
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
                sourceLedgerList = getAllSourceLedgerUseCase(),
                currentActiveSourceLedgerId = localRepository.getCurrentSelectedSourceLedgerId(),
                selectedSourceLedgerIdFromList = localRepository.getCurrentSelectedSourceLedgerId(),
                selectedSourceLedgerIndexFromList = -1
            )
            state = state.copy(
                isLoading = false,
            )
        }
    }

    private fun hideChangeSourceLedgerDialog(){
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                isChangeSourceLedgerDialogVisible = false,
                selectedSourceLedgerIdFromList = 0,
                selectedSourceLedgerIndexFromList = 0
            )
        }
    }

    private fun onSourceLedgerSelected(index: Int, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
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