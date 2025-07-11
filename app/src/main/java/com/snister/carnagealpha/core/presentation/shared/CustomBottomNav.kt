package com.snister.carnagealpha.core.presentation.shared

import android.widget.Toast
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.snister.carnagealpha.ui.theme.*


data class BottomNavItem(
    val menuName: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNew: Boolean = false,
    val badgeCounter: Int = 0
)

val bottomNavItems = listOf(
    BottomNavItem(
        menuName = "Dashboard",
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Filled.Home,
    ),

    BottomNavItem(
        menuName = "Planning",
        route = "planning",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Filled.Favorite,
    ),

    BottomNavItem(
        menuName = "Tools",
        route = "tools",
        selectedIcon = Icons.Filled.Build,
        unselectedIcon = Icons.Filled.Build,
        badgeCounter = 2
    ),

    BottomNavItem(
        menuName = "Me",
        route = "profile",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Filled.AccountCircle,
    ),
)
@Composable
fun CustomBottomNav(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentContext = LocalContext.current
    var isSelected by remember {
        mutableIntStateOf(0)
    }

    NavigationBar(
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        bottomNavItems.forEachIndexed{
            index, bottomNavItem ->
            NavigationBarItem(
//                modifier = modifier
//                    .border(1.dp, cDC5F00),
                selected = index == isSelected,
                colors = NavigationBarItemColors(
                    selectedIconColor = cA91D3A,
                    selectedTextColor = cA91D3A,
                    unselectedIconColor = c686D76,
                    unselectedTextColor = c686D76,
                    selectedIndicatorColor = cEEEEEE,
                    disabledIconColor = c373A40,
                    disabledTextColor = c373A40
                ),
                onClick = {
                    isSelected = index
//                    Toast.makeText(
//                        currentContext, "Selected index = $index", Toast.LENGTH_LONG
//                    ).show()
                },
                icon = {
                    BadgedBox(
                        modifier = modifier,
                        badge = {
                            if(bottomNavItem.badgeCounter > 0){
                                Badge(
                                    containerColor = cC73659,
                                    contentColor = cEEEEEE
                                ) {
                                    Text(text = "${bottomNavItem.badgeCounter}")
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = bottomNavItem.selectedIcon,
                            contentDescription = bottomNavItem.menuName
                        )
                    }
                },

                label = {
                    Text(text = bottomNavItem.menuName)
                }
            )
        }
    }
}

@Composable
@Preview
fun CustomBottomNavPreview(){

}