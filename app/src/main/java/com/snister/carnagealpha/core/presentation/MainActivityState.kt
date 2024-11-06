package com.snister.carnagealpha.core.presentation

data class MainActivityState(
    val currentSourceLedgerBalance: Long = 0,
    val isChangeSourceLedgerDialogVisible: Boolean = false
)
