package com.snister.carnagealpha.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.snister.carnagealpha.R

@Composable
fun BalanceCard(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 40.dp)
//                .height(100.dp)
//                .clip(RoundedCornerShape(28.dp))
//                .background(
//                    brush = Brush.horizontalGradient(
//                        listOf(Color.Green, Color.Yellow)
//                    )
//                )
//
//        )

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(12.dp)
        ){
            CardContent(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CardContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.error
                )
            )
        )
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.TopStart)
        ){

        }
    }
}