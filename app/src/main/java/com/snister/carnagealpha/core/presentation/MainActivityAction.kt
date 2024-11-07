package com.snister.carnagealpha.core.presentation

interface MainActivityAction {

//    data object InsertInitialDefaultSourceLedger: MainActivityAction
    data object LoadSourceLedger: MainActivityAction

    data object ShowChangeSourceLedgerDialog: MainActivityAction

    data object HideChangeSourceLedgerDialog: MainActivityAction

    data class OnSourceLedgerItemSelected(
        val selectedSourceLedgerIndex: Int,
        val selectedSourceLedgerId: Int
    ): MainActivityAction

    data object OnSaveSelectedSourceLedger: MainActivityAction
}