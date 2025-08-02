package com.snister.carnagealpha.core.presentation

sealed interface MainActivityEvents {
    data object UpsertSourceLedgerSuccess: MainActivityEvents
    data object UpsertSourceLedgerFailed: MainActivityEvents
//    data object AskForNotificationPermission: MainActivityEvents
}