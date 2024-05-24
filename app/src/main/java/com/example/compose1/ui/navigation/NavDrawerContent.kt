package com.example.compose1.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose1.R
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
                text = "Категории",
                fontSize = 25.sp,
                //fontStyle = FontStyle.Italic,
                //fontFamily = FontFamily.Serif
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
        }
    )
}

@Composable
fun DrawerHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(colorResource(id = R.color.app_background)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_person_78),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Пользователь",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun DrawerBody(
    navController: NavController
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight())
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_format_list_bulleted_78),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("MainScreen") {
                            popUpTo("MainScreen") { inclusive = true }
                        }
                    },
                fontSize = 16.sp,
                text = "Главный экран",
                textAlign = TextAlign.Start
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_account_balance_78),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp)
                    .clickable {
                        navController.navigate("AccountScreen") {
                            popUpTo("AccountScreen") { inclusive = true }
                        }
                    },
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                text = "Счета"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_filter_list_78),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp)
                    .clickable {
                        navController.navigate("CategoryScreen") {
                            popUpTo("CategoryScreen") { inclusive = true }
                        }
                    },
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                text = "Категории"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_access_time_78),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                text = "Регулярные платежи"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_timeline_78),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                text = "Графики расходов"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_query_stats_78),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                text = "Прогноз трат"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_calculate_78),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                text = "Кредитный калькулятор"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_calculate_78),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                text = "Калькулятор вкладов"
            )
        }
    }
}