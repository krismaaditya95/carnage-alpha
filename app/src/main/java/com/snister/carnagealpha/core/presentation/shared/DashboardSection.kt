package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.ui.theme.cmykBlue
import com.snister.carnagealpha.ui.theme.cmykRed

@Composable
fun DashboardSection(
    sectionTitle: String = "SectionTitle",
    onSeeAllClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
){
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp),
//            .border(1.dp, cmykRed),
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sectionTitle,
                color = cmykBlue,
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 14.sp,
            )

            TextButton(
                onClick = {},
                contentPadding = PaddingValues(all = 0.dp)
            ) {
                Text(
                    text = "See All",
                    color = cmykBlue,
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    fontSize = 14.sp,
                )
            }
        }

        // ANY WIDGET FROM PARAMETER GOES HERE
        content()
    }
}


@Composable
@Preview(showBackground = true)
fun DashboardSectionPreview(){
    DashboardSection(

    )
}