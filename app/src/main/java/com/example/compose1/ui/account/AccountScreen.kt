package com.example.compose1.ui.account

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose1.db.entities.Account
import com.example.compose1.ui.navigation.DrawerBody
import com.example.compose1.ui.navigation.DrawerHeader
import com.example.compose1.ui.navigation.TopAppBarContent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    navController: NavController
){
    val accountViewModel = viewModel(modelClass = AccountViewModel::class.java)
    val accountState = accountViewModel.state
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
        Scaffold (
            topBar = {
                TopAppBarContent(scope, drawerState)
            }
        ){
            Column {
                Spacer(modifier = Modifier.size(it.calculateTopPadding()))
                AccountEntry(
                    state = accountState,
                    onAccountNameChange = accountViewModel::onAccountNameChange,
                    onAccountSumChange = accountViewModel::onAccountSumChange,
                    onSaveAccount = accountViewModel::addAccount,
                    modifier = Modifier.fillMaxHeight(0.2f)
                ) {
                    navController.navigate("MainScreen")
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    items(accountState.accountList) {
                        Accounts(account = it) {
                            accountViewModel.deleteAccount(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountEntry(
    modifier: Modifier = Modifier,
    state: AccountState,
    onAccountNameChange:(String) -> Unit,
    onAccountSumChange:(String) -> Unit,
    onSaveAccount:() -> Unit,
    navController: () -> Unit
){
    Column (
        modifier = Modifier.padding(10.dp)
    ){
        TextField(
            value = state.accountName,
            onValueChange = {onAccountNameChange(it)},
            label = { Text(text = "Account name")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(15.dp))
        TextField(
            value = state.accountSum,
            onValueChange = {onAccountSumChange(it)},
            label = { Text(text = "Account sum")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.Companion.size(15.dp))
        Button(
            onClick = {
                     onSaveAccount.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.accountName.isNotEmpty() && state.accountSum.isNotEmpty(),
        ) {
            Text(text = "Add account")
        }
    }
}

@Composable
fun Accounts(
    account: Account,
    onAccountClick: () -> Unit
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onAccountClick.invoke()
            }
            .padding(5.dp)
    ) {
        Row {
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = account.accountName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = account.accountSum,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}