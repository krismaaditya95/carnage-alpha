package com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.core.utils.CurrencyFormatter
import com.snister.carnagealpha.ui.theme.*

@Composable
fun MinimizedBalanceCard(
    onBalanceClick: () -> Unit,
    sourceLedgerName: String = "",
    balance: Long
) {
    Box{
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(12.dp)
        ){
            CardContent(
                onBalanceClick = onBalanceClick,
                balance = balance,
                sourceLedgerName = sourceLedgerName
            )
        }
    }
}

@Composable
fun CardContent(
    onBalanceClick: () -> Unit,
    balance: Long,
    sourceLedgerName: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        c151515,
                        cEEEEEE
                    ),
                    start = Offset(30.0f, 30.0f),
                    end = Offset(800.0f, 800.0f)
                )
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(20.dp)
                .padding(start = 20.dp, bottom = 20.dp)
                .align(Alignment.TopStart),
        ){
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = sourceLedgerName,
                    color = cDC5F00.copy(0.6f),
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(0.7f),
//                        .border(1.dp, cC73659),
                    textAlign = TextAlign.Start
                )
                TextButton(
                    modifier = Modifier
                        .weight(0.3f),
//                        .border(1.dp, cC73659),
                    onClick = {

                    }
                ) {
                    Text(
//                        modifier = Modifier
//                            .border(1.dp, cC73659),
                        text = "change",
                        color = cC73659
                    )
                }

            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 10.dp)
            )

            Text(
                text = "Balance",
                color = cDC5F00.copy(0.6f),
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 20.sp
            )
            Text(
//                text = "Rp. $balance",
                text = CurrencyFormatter.formatToRupiah(balance),
                color = cDC5F00.copy(0.6f),
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                modifier = Modifier.clickable (enabled = true){
                    onBalanceClick()
                }
            )
        }
    }
}

@Composable
@Preview
fun BalanceCardPreview(modifier: Modifier = Modifier) {
    MinimizedBalanceCard(
        onBalanceClick = {},
        balance = 1000000,
        sourceLedgerName = "SourceLedgerName"
    )
}