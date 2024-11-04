package com.snister.carnagealpha.features.expense_tracker.data.mapper

import com.snister.carnagealpha.features.expense_tracker.data.models.SpendingDataModel
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.Instant
import java.time.ZoneId

fun SpendingDataModel.toSpendingEntity() : SpendingEntity = SpendingEntity(
    spendingId = spendingId ?: 0,
    spendingName = spendingName,
    spendingAmount = spendingAmount,
    dateTime = Instant.parse(dateTime).atZone(ZoneId.systemDefault()),
    sourceLedgerId = sourceLedgerId
)

fun SpendingEntity.toSpendingDataModel() : SpendingDataModel = SpendingDataModel(
    spendingName = spendingName,
    spendingAmount = spendingAmount,
    dateTime = dateTime.toInstant().toString(),
    sourceLedgerId = sourceLedgerId
)

fun SpendingEntity.toEditSpendingDataModel() : SpendingDataModel = SpendingDataModel(
    spendingId = spendingId,
    spendingName = spendingName,
    spendingAmount = spendingAmount,
    dateTime = dateTime.toInstant().toString(),
    sourceLedgerId = sourceLedgerId
)