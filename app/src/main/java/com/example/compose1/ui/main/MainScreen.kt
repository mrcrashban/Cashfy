package com.example.compose1.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose1.R
import com.example.compose1.db.TransactionsWithAccountAndCategory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {
    val mainViewModel = viewModel(modelClass = MainViewModel::class.java)
    val mainState = mainViewModel.state
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("CategoryScreen") }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        },
        topBar = {
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
                       // scope.launch {
                        //    drawerState.apply {
                        //        if (isClosed) open() else close()
                         //   }
                       // }
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
    ){
        LazyColumn (
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp)
                .background(Color.Green)

        ){
            items(mainState.transactions) {
                TransactionListItem(transaction = it) {
                  //  onNavigate.invoke(it.transaction.uidTransaction)
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
                Text(text = transaction.category.categoryName, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = transaction.transaction.uidTransaction.toString(), fontSize = 18.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = transaction.transaction.sum.toString(), textAlign = TextAlign.Right, fontSize = 18.sp)
            }
        }
    }
}