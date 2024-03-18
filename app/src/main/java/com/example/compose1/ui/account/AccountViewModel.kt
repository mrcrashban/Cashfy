package com.example.compose1.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose1.Graph
import com.example.compose1.db.entities.Account
import com.example.compose1.db.entities.Category
import com.example.compose1.ui.category.CategoryState
import com.example.compose1.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository: Repository = Graph.repository
) : ViewModel(){
    var state by mutableStateOf(AccountState())
        private set

    init {
        getAccounts()
    }

    private fun getAccounts(){
        viewModelScope.launch {
            repository.account.collectLatest {
                state = state.copy(
                    accountList = it
                )
            }
        }
    }

    fun addAccount(){
        viewModelScope.launch {
            repository.insertAccount(
                Account(
                    accountName = state.accountName,
                    accountSum = state.accountSum
                )
            )
        }
    }

    fun onAccountNameChange(newValue:String){
        state = state.copy(accountName = newValue)
    }

    fun onAccountSumChange(newValue:String){
        state = state.copy(accountSum = newValue)
    }

    fun deleteAccount(account: Account){
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
}

data class AccountState(
    val accountList: List<Account> = emptyList(),
    val accountName: String = "",
    val accountSum: String = ""
)