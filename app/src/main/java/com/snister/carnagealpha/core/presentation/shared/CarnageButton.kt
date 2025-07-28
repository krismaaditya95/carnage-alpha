package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snister.carnagealpha.ui.theme.*

@Composable
fun CarnageButton(
    modifier: Modifier = Modifier,
    buttonTitle: String = "CarnageButton",
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .height(IntrinsicSize.Min),
        colors = ButtonColors(
            containerColor = cC73659,
            contentColor = cEEEEEE,
            disabledContentColor = cEEEEEE,
            disabledContainerColor = c686D76
        ),
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(width = 1.dp, color = cmykBlue),
        //                    .padding(start = 20.dp),
        onClick = onClick
    ) {
        Text(
            text = buttonTitle
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CarnageButtonPreview(){
    CarnageButton(
        onClick = {}
    )
}