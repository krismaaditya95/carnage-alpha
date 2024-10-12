package com.snister.carnagealpha.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar (
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
){
    TopAppBar(
        title = {
            Row {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, top = 20.dp)
                        .align(Alignment.Top),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(
//                        modifier = Modifier.padding(end = 20.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .width(250.dp)
                                .height(40.dp)
                                .background(cC73659)
                                .align(Alignment.CenterStart)
                        )

                        Text(
                            text = "Catatan Pengeluaran",
                            fontSize = 24.sp,
                            //fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                            color = cEEEEEE,
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .align(Alignment.CenterStart)
                        )
                    }

                    Box (
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(44.dp)
                            .background(cC73659)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.profile_picture),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                //.clip(RoundedCornerShape(12.dp))
                                .align(Alignment.Center)
                        )
                    }
                }
            }

        },
        scrollBehavior = scrollBehavior,
    )
}