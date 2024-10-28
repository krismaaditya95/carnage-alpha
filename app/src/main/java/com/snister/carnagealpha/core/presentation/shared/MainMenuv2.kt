package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.ui.theme.*

@Composable
fun MainMenuv2(
    modifier: Modifier = Modifier,
    onAddSpendingClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onOtherClick: () -> Unit
) {
    Row (
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        MenuItemv2(
            icon = painterResource(id = R.drawable.receive_money),
            title = "Income",
            color = cmykGreen,
            onClick = onAddIncomeClick,
            modifier = modifier.weight(1f)
        )
        MenuItemv2(
            icon = painterResource(id = R.drawable.send_money),
            title = "Expense",
            color = cC73659,
            onClick = onAddSpendingClick,
            modifier = modifier.weight(1f)
        )
//        MenuItemv2(
//            icon = painterResource(id = R.drawable.other_menu),
//            title = "Lainnya",
//            color = cC73659,
//            iconSize = 24.dp,
//            onClick = onOtherClick
//        )
    }
}

@Composable
fun MenuItemv2(
    modifier: Modifier,
    icon: Painter,
    title: String,
    color: Color,
    onClick: () -> Unit
) {
    ElevatedCard (
        onClick = { onClick() },
        modifier = modifier
            .padding(start = 4.dp, end = 4.dp)
            .border(1.dp, color, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp)
    ){
        Row {
            Box(
                modifier = Modifier.padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = title,
                    modifier = Modifier
                        .size(30.dp),
                    tint = color
                )
            }

            Text(
                modifier = Modifier.padding(10.dp).align(Alignment.CenterVertically),
                text = title,
                color = color,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
@Preview
fun MainMenuv2Preview(modifier: Modifier = Modifier) {
    MainMenuv2(
        onAddIncomeClick = {},
        onOtherClick = {},
        onAddSpendingClick = {}
    )
}