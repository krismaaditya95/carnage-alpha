package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.material3.LocalContentColor as LocalContentColor1

@Composable
fun BalanceCardV3(
    onBalanceClick: () -> Unit,
    onChangeSourceLedgerClick: () -> Unit,
    sourceLedgerName: String = "",
    balance: Long,
    cardContainerColor: Color? = null,
    sourceLedgerFontColor: Color? = null,
    balanceFontColor: Color? = null
) {
    ElevatedCard(
//        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = (cardContainerColor ?: MaterialTheme.colorScheme.surfaceContainerLow)
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, bottom = 20.dp)
        ){
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = sourceLedgerName,
                    color = (sourceLedgerFontColor ?: LocalContentColor1.current).copy(0.8f),
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
                    onClick = { onChangeSourceLedgerClick() }
                ) {
                    Text(
//                        modifier = Modifier
//                            .border(1.dp, cC73659),
                        text = "change",
                        color = (sourceLedgerFontColor ?: LocalContentColor.current)
                    )
                }

            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 10.dp)
            )
            Text(
                text = "Balance",
                color = (balanceFontColor ?: LocalContentColor.current).copy(0.8f),
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 20.sp
            )
            Text(
                text = CurrencyFormatter.formatToRupiah(balance),
                color = (balanceFontColor ?: LocalContentColor.current).copy(0.8f),
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
// ==================================================
@Composable
@Preview(showBackground = true)
fun BalanceCardV3Preview(modifier: Modifier = Modifier) {
    Column{
        BalanceCardV3(
            onBalanceClick = {},
            onChangeSourceLedgerClick = {},
            balance = 1000000,
            sourceLedgerName = "SourceLedgerName"
        )
    }
}