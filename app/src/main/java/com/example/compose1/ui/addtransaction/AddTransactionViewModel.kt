package com.example.compose1.ui.addtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose1.Graph
import com.example.compose1.db.entities.Account
import com.example.compose1.db.entities.Category
import com.example.compose1.db.entities.Transaction
import com.example.compose1.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class AddTransactionViewModel(
    private val repository: Repository = Graph.repository
):ViewModel() {
    var state by mutableStateOf(TransactionState())
        private set

    init {
        getCategories()
        getAccounts()
    }
    private fun getCategories(){
        viewModelScope.launch {
            repository.category.collectLatest {
                state = state.copy(
                    categoryList = it
                )
            }
        }
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

    fun AddTransaction(){
        viewModelScope.launch {
            repository.insertTransaction(
                Transaction(
                    accountIid = state.accountList.find {
                        it.accountName == state.account
                    }?.uidAccount ?: 0,
                    categoryId = state.categoryList.find {
                        it.categoryName == state.category
                    }?.uidCategory ?: 0,
                    sum = state.sum.toDouble(),
                    type = state.type,
                    date = state.date,
                    comment = state.comment
                )
            )
        }
    }

    fun onSumChange(newValue:String){
        state = state.copy(sum = newValue)
    }

    fun onTypeChange(newValue:String){
        state = state.copy(type = newValue)
    }

    fun onDateChange(newValue: Date) {
        state = state.copy(date = newValue)
    }

    fun onCommentChange(newValue:String){
        state = state.copy(comment = newValue)
    }

    fun onCategoryChange(newValue: String){
        state = state.copy(category = newValue)
    }

    fun onAccountChange(newValue: String){
        state = state.copy(account = newValue)
    }
}

data class TransactionState(
    val accountList: List<Account> = emptyList(),
    val categoryList: List<Category> = emptyList(),
    val account: String = "",
    val category: String = "",
    val sum: String = "",
    val type: String = "",
    val date: Date = Date(),
    val comment: String = "",


    )