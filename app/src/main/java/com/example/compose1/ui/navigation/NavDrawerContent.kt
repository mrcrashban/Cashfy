package com.example.compose1.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(scope: CoroutineScope, drawerState: DrawerState){
    CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary
    ),
        title = {
            Text(
                text = "Cashfy",
                fontSize = 35.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "User info",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DrawerBody(
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxWidth()){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
                .clickable {
                    navController.navigate("MainScreen") {
                        popUpTo("MainScreen") { inclusive = true }
                    }
                },
            text = "Main screen"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
                .clickable {
                    navController.navigate("AccountScreen") {
                        popUpTo("AccountScreen") { inclusive = true }
                    }
                },
            text = "Accounts"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
                .clickable {
                    navController.navigate("CategoryScreen"){
                        popUpTo("CategoryScreen") {inclusive = true}
                    }
                },
            text = "Categories"
        )
    }
}