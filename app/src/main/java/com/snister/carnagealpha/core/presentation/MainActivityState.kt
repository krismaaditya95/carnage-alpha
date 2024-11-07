package com.snister.carnagealpha.core.presentation

import androidx.compose.ui.graphics.Color
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.ui.theme.*

data class MainActivityState(
    val currentSourceLedgerBalance: Long = 0,
    val isChangeSourceLedgerDialogVisible: Boolean = false,
    val isLoading: Boolean = false,
    val sourceLedgerList: List<SourceLedgerEntity> = emptyList(),
    val selectedSourceLedgerIndexFromList: Int = 0,
    val selectedSourceLedgerIdFromList: Int = 0,
    val currentActiveSourceLedgerId: Int = 0,
)
