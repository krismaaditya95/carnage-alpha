package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.core.utils.CurrencyFormatter
import com.snister.carnagealpha.core.utils.StringDateFormatter
import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.ui.theme.*
import java.time.ZonedDateTime

@Composable
fun IncomeListWidget(
    modifier: Modifier = Modifier,
    state: IncomeOverviewState,
    onDeleteIncome: (Int) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(start = 20.dp, end = 20.dp),
        contentPadding = PaddingValues(top = 14.dp)
    ) {
        itemsIndexed(state.incomeList){
            index, item ->
            IncomeItemWidget(
                incomeItem = item,
                onDeleteIncome = {
                    onDeleteIncome(index)
                }
            )
        }
    }
}

@Composable
fun IncomeItemWidget(
    modifier: Modifier = Modifier,
    incomeItem: IncomeEntity,
    onDeleteIncome: (Int) -> Unit
) {
    ElevatedCard(
        onClick = {  },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(bottom = 14.dp)
            .border(1.dp, cmykGreen, RoundedCornerShape(4.dp)),
        shape = RoundedCornerShape(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
//                .background(Color.White)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column{
                Text(
                    text = "${incomeItem.incomeSourceName} | SLID = ${incomeItem.sourceLedgerId}",
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .padding(bottom = 4.dp)
                )

                Text(
                    text = StringDateFormatter.toDayMonthYearAtHourMinute(incomeItem.dateTime),
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    fontSize = 11.sp
                )
            }

            Text(
                text = "+ ${CurrencyFormatter.formatToRupiah(incomeItem.incomeAmount)}",
                color = cmykGreen,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
@Preview
fun IncomeItemWidgetPreview(modifier: Modifier = Modifier) {
    IncomeItemWidget(
        onDeleteIncome = {},
        incomeItem = IncomeEntity(
            incomeId = 1,
            dateTime = ZonedDateTime.now(),
            incomeSourceName = "Gaji bulan ini",
            incomeAmount = 4000000,
            sourceLedgerId = 1
        )
    )
}