package com.example.compose1.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose1.Graph
import com.example.compose1.db.TransactionsWithAccountAndCategory
import com.example.compose1.db.entities.Transaction
import com.example.compose1.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository = Graph.repository
):ViewModel()
{
    var state by mutableStateOf(MainState())
        private set

    init {
        getTransactions()
    }

    private fun getTransactions(){
        viewModelScope.launch {
            repository.transaction.collectLatest {
                state = state.copy(
                    transactions = it
                )
            }
        }
    }

    fun deleteTransaction(transaction: Transaction){
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }


}

data class MainState(
    val transactions:List<TransactionsWithAccountAndCategory> = emptyList()
)