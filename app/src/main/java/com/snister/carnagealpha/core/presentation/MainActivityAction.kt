package com.snister.carnagealpha.core.presentation

interface MainActivityAction {

//    data object InsertInitialDefaultSourceLedger: MainActivityAction
    data object LoadSourceLedger: MainActivityAction

    data object ShowChangeSourceLedgerDialog: MainActivityAction

    data object HideChangeSourceLedgerDialog: MainActivityAction

    data class OnSourceLedgerItemSelected(val selectedSourceLedgerId: Int): MainActivityAction

    data object OnSaveSelectedSourceLedger: MainActivityAction
}