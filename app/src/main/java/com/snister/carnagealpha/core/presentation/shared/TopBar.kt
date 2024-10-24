package com.snister.carnagealpha.core.presentation.shared

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar (
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    appBarTitle: String = "Money Manager v1.0",
    navigationIcon: Boolean = false
){

    var notificationCount by remember {
        mutableIntStateOf(4)
    }

    TopAppBar(
        title = {
            Row {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp)
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
                            text = appBarTitle,
                            fontSize = 24.sp,
                            //fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                            color = cEEEEEE,
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .align(Alignment.CenterStart)
                        )
                    }
                }
            }

        },
        navigationIcon = {
            if(navigationIcon){
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to previous screen"
                    )
                }
            }
        },
        actions = {
//            IconButton(
//                onClick = {}
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Notifications,
//                    contentDescription = "Notification")
//            }

            IconButton(
//                modifier = modifier.padding(end = 4.dp),
                onClick = {}
            ) {
                BadgedBox(
                    badge = {
                        if (notificationCount > 0) {
                            Badge(
                                containerColor = cC73659,
                                contentColor = cEEEEEE
                            ) {
                                Text(text = "$notificationCount")
                            }
                        }
                    }
                ) {
                    //                IconButton(
                    //                    onClick = {}
                    //                ) {
                    //                    Icon(
                    //                        imageVector = Icons.Filled.Notifications,
                    //                        contentDescription = "Notification"
                    //                    )
                    //                }
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notification"
                    )
                }
            }

            Box (
                modifier = Modifier
                    .padding(end = 20.dp, start = 10.dp)
                    .clip(CircleShape)
                    .size(34.dp)
                    .background(cC73659)
            ){
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        //.clip(RoundedCornerShape(12.dp))
                        .align(Alignment.Center)
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}