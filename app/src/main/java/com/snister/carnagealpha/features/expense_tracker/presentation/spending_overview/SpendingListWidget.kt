package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.core.presentation.shared.SpendingItem
import com.snister.carnagealpha.core.utils.CurrencyFormatter
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.ui.theme.*
import java.time.ZonedDateTime

@Composable
fun SpendingListWidget(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onDeleteSpending: (Int) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(start = 20.dp, end = 20.dp),
        contentPadding = PaddingValues(top = 14.dp)
    ) {
        itemsIndexed(state.spendingList){
                index, item ->
            SpendingItemWidget(
                spendingItem = item,
                onDeleteSpending = {
                    onDeleteSpending(index)
                }
            )
        }
    }

}

@Composable
fun SpendingItemWidget(
    modifier: Modifier = Modifier,
    spendingItem: SpendingEntity,
    onDeleteSpending: (Int) -> Unit
) {
    ElevatedCard(
        onClick = {  },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(bottom = 14.dp)
            .border(1.dp, cDC5F00, RoundedCornerShape(4.dp)),
        shape = RoundedCornerShape(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column{
                Text(
                    text = spendingItem.spendingName,
                    color = c151515,
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .padding(bottom = 4.dp)
                )

                Text(
//                    text = "${spendingItem.dateTime.dayOfMonth}-${spendingItem.dateTime.month}-${spendingItem.dateTime.year}" +
//                            "at ${spendingItem.dateTime.hour}:${spendingItem.dateTime.minute}",
                    text = "${spendingItem.dateTime.hour}:${spendingItem.dateTime.minute}",
                    color = cC73659,
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    fontSize = 12.sp
                )
            }

            Text(
                text = "- ${CurrencyFormatter.formatToRupiah(spendingItem.spendingAmount.toLong())}",
                color = cC73659,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
@Preview
fun SpendingItemWidgetPreview(modifier: Modifier = Modifier) {
    SpendingItemWidget(
        onDeleteSpending = {},
        spendingItem = SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli seblak",
            spendingAmount = 10000
        )
    )
}