package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalContentColor
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

enum class TransactionType{
    Default,
    Income,
    Expense
}

@Composable
fun TransactionType.getColor(): Color {
    return when (this) {
        TransactionType.Income -> cmykBlue
        TransactionType.Expense -> cA91D3A
        TransactionType.Default -> LocalContentColor.current.copy(alpha = 0.8f)
    }
}

@Composable
fun TransactionType.getWording(nominal: Long): String {
    return when (this) {
        TransactionType.Income -> "+ ${CurrencyFormatter.formatToRupiah(nominal)}"
        TransactionType.Expense -> "- ${CurrencyFormatter.formatToRupiah(nominal)}"
        TransactionType.Default -> CurrencyFormatter.formatToRupiah(nominal)
    }
}

@Composable
fun TransactionItemWidget(
    modifier: Modifier = Modifier,
    incomeItem: IncomeEntity,
    onDeleteIncome: (Int) -> Unit,
    transactionType: TransactionType = TransactionType.Default
) {
    ElevatedCard(
        onClick = {  },
        modifier = Modifier
            .fillMaxWidth()
//            .height(IntrinsicSize.Min)
            .wrapContentHeight()
            .padding(all = 14.dp)
            .border(1.2.dp, transactionType.getColor(), RoundedCornerShape(6.dp)),
        shape = RoundedCornerShape(6.dp),
    ) {
        Row(
            modifier = Modifier
//                .fillMaxSize()
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column{
                Text(
                    text = incomeItem.incomeSourceName,
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
                text = transactionType.getWording(incomeItem.incomeAmount),
                color = transactionType.getColor(),
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TransactionItemWidgetPreview(modifier: Modifier = Modifier) {
    TransactionItemWidget(
        transactionType = TransactionType.Income,
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