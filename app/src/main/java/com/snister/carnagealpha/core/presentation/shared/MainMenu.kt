package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.ui.theme.*

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    onAddSpendingClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onOtherClick: () -> Unit
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        MenuItem(
            icon = painterResource(id = R.drawable.send_money),
            title = "Pengeluaran",
            color = cC73659,
            iconSize = 30.dp,
            onClick = onAddSpendingClick
        )
        MenuItem(
            icon = painterResource(id = R.drawable.receive_money),
            title = "Pemasukan",
            color = cC73659,
            iconSize = 30.dp,
            onClick = onAddIncomeClick
        )
        MenuItem(
            icon = painterResource(id = R.drawable.other_menu),
            title = "Lainnya",
            color = cC73659,
            iconSize = 24.dp,
            onClick = onOtherClick
        )
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    iconSize: Dp,
    title: String,
    color: Color,
    onClick: () -> Unit
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ElevatedCard (
            onClick = { onClick() },
            modifier = Modifier
                .size(60.dp),
            shape = RoundedCornerShape(50.dp)
        ){
            Box(
                modifier = Modifier
//                    .clip(CircleShape)
                    .size(60.dp)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = icon,
                    contentDescription = title,
                    modifier = Modifier
                        .size(iconSize),
                    tint = cEEEEEE
                )
            }
        }

        Text(
            text = title,
            color = cA91D3A,
            fontFamily = Font(R.font.roboto_regular).toFontFamily(),
            fontSize = 14.sp
        )
    }
}