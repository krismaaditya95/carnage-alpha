package com.snister.carnagealpha.features.expense_tracker.data.mapper

import com.snister.carnagealpha.features.expense_tracker.data.models.SourceLedgerDataModel
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity

fun SourceLedgerDataModel.toSourceLedgerEntity(): SourceLedgerEntity = SourceLedgerEntity(
    sourceLedgerId = sourceLedgerId ?: 0,
    sourceLedgerName = sourceLedgerName,
    sourceLedgerBalance = sourceLedgerBalance
)

fun SourceLedgerEntity.toSourceLedgerDataModel() : SourceLedgerDataModel = SourceLedgerDataModel(
    sourceLedgerName = sourceLedgerName,
    sourceLedgerBalance = sourceLedgerBalance
)

fun SourceLedgerEntity.toEditSourceLedgerDataModel() : SourceLedgerDataModel = SourceLedgerDataModel(
    sourceLedgerId = sourceLedgerId,
    sourceLedgerName = sourceLedgerName,
    sourceLedgerBalance = sourceLedgerBalance,
)