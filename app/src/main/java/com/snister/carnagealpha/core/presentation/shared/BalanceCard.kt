package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.core.utils.CurrencyFormatter
import com.snister.carnagealpha.ui.theme.*
import java.text.NumberFormat

@Composable
fun BalanceCard(
    modifier: Modifier = Modifier,
    onBalanceClick: () -> Unit,
    balance: Long
) {
    Box(modifier = modifier) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(12.dp)
        ){
            CardContent(
                modifier = Modifier.fillMaxSize(),
                onBalanceClick = onBalanceClick,
                balance = balance
            )
        }
    }
}

@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    onBalanceClick: () -> Unit,
    balance: Long
) {
    Box(
        modifier = modifier.background(
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
                .padding(20.dp)
                .align(Alignment.TopStart),
        ){
            Text(
                text = "Hi, User",
                color = cDC5F00.copy(0.6f),
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 22.sp
            )
            Text(
//                text = "Rp. $balance",
                text = CurrencyFormatter.formatToRupiah(balance),
                color = cDC5F00.copy(0.6f),
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 28.sp,
                modifier = Modifier.clickable (enabled = true){
                    onBalanceClick()
                }
            )
        }

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){

            Text(
                text = "1234567890",
                color = cA91D3A,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 22.sp
            )
            Image(
                painter = painterResource(id = R.drawable.mastercard),
                contentDescription = null,
                modifier = Modifier.width(60.dp),
            )
        }
    }
}

@Composable
@Preview
fun BalanceCardPreview(){
    BalanceCard(
        onBalanceClick = {},
        balance = 500000
    )
}

@Composable
@Preview
fun CardContentPreview(){
    CardContent(
        onBalanceClick = {},
        balance = 1000000
    )
}