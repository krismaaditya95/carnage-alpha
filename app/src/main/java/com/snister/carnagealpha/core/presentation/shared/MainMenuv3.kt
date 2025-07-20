package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.ui.theme.*

@Composable
fun MainMenuV3(
    modifier: Modifier = Modifier,
    onAddSpendingClick: () -> Unit,
    onAddIncomeClick: () -> Unit
) {
    Row (
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        MenuItemV3(
            icon = painterResource(id = R.drawable.receive_money),
            title = "Income",
            color = cmykBlue,
            onClick = onAddIncomeClick,
            modifier = modifier.weight(1f)
        )
        MenuItemV3(
            icon = painterResource(id = R.drawable.send_money),
            title = "Expense",
            color = cA91D3A,
            onClick = onAddSpendingClick,
            modifier = modifier.weight(1f)
        )
    }
}

@Composable
fun MenuItemV3(
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
            .border(1.dp, color, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
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
@Preview(showBackground = true)
fun MainMenuV3Preview(modifier: Modifier = Modifier) {
    MainMenuV3(
        onAddIncomeClick = {},
        onAddSpendingClick = {}
    )
}