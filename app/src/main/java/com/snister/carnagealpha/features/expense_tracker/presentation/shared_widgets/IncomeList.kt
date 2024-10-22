package com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.ui.theme.*

class IncomeItem(
    name: String,
    amount: Long,
    color: Color,
    icon: Painter
){
    val name = name
    val amount = amount
    val color = color
    val icon = icon
}

@Composable
fun IncomeListWidget(modifier: Modifier = Modifier) {
    val incomeItem = listOf(
        IncomeItem(
            name = "Gaji",
            amount = 4400000,
            color = cC73659,
            icon = painterResource(id = R.drawable.other_menu)
        ),
        IncomeItem(
            name = "Freelance",
            amount = 1000000,
            color = cC73659,
            icon = painterResource(id = R.drawable.other_menu)
        ),
        IncomeItem(
            name = "Ditransfer ortu",
            amount = 100000,
            color = cC73659,
            icon = painterResource(id = R.drawable.other_menu)
        ),
    )

    Box(modifier = modifier){
        Column {
            Text(
                text = "Pemasukan",
                color = cDC5F00,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            LazyColumn{
                itemsIndexed(incomeItem){
                    index, incomeItem ->
                    IncomeItems(incomeItem = incomeItem)
//                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }



}

@Composable
fun IncomeItems(
    modifier: Modifier = Modifier,
    incomeItem: IncomeItem
) {
    ElevatedCard(
        onClick = {  },
        modifier = Modifier
            .width(120.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(cDC5F00)
            .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = incomeItem.icon,
                contentDescription = incomeItem.name,
                modifier = Modifier.size(10.dp),
                tint = cEEEEEE
            )
            Text(
                text = incomeItem.name,
                color = cEEEEEE,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 14.sp
            )
            Text(
                text = "Rp. " + incomeItem.amount.toString(),
                color = cEEEEEE,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 10.sp
            )
        }
    }
}