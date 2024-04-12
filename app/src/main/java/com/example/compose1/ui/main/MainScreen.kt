package com.example.compose1.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose1.db.TransactionsWithAccountAndCategory
import com.example.compose1.ui.navigation.DrawerBody
import com.example.compose1.ui.navigation.DrawerHeader
import com.example.compose1.ui.navigation.TopAppBarContent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MainScreen(
    navController: NavController,
    onNavigate: (Int) -> Unit
) {
    val mainViewModel = viewModel(modelClass = MainViewModel::class.java)
    val mainState = mainViewModel.state
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(navController)
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { onNavigate.invoke(-1) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            },
            topBar = {
                TopAppBarContent(scope, drawerState)
            }
        ) {
            Column {
                Spacer(modifier = Modifier.size(it.calculateTopPadding()))
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(10.dp)
                ) {
                    items(mainState.transactions) {
                        TransactionListItem(transaction = it) {
                              onNavigate.invoke(it.transaction.uidTransaction)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionListItem(
    transaction: TransactionsWithAccountAndCategory,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke()
            }
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp)
    ){
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Text(text = transaction.account.accountName, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = transaction.category.categoryName, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = transaction.transaction.sum.toString(), fontSize = 18.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = transaction.transaction.date.toString(), fontSize = 18.sp)
            }
        }
    }
}

fun formatDate(date: Date): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)