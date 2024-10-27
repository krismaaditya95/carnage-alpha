package com.snister.carnagealpha.features.expense_tracker.data.mapper

import com.snister.carnagealpha.features.expense_tracker.data.models.IncomeDataModel
import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import java.time.Instant
import java.time.ZoneId

fun IncomeDataModel.toIncomeEntity() : IncomeEntity = IncomeEntity(
    incomeId = incomeId ?: 0,
    incomeSourceName = incomeSourceName,
    incomeAmount = incomeAmount,
    dateTime = Instant.parse(dateTime).atZone(ZoneId.systemDefault())
)

fun IncomeEntity.toIncomeDataModel() : IncomeDataModel = IncomeDataModel(
    incomeSourceName = incomeSourceName,
    incomeAmount = incomeAmount,
    dateTime = dateTime.toInstant().toString()
)

