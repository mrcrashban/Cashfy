package com.example.compose1.ui.repository

import com.example.compose1.db.AccountDao
import com.example.compose1.db.CategoryDao
import com.example.compose1.db.TransactionDao
import com.example.compose1.db.entities.Account
import com.example.compose1.db.entities.Category
import com.example.compose1.db.entities.Transaction

class Repository(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val accountDao: AccountDao
) {
    val transaction = transactionDao.getTransactionsWithAccountAndCategory()
    val category = categoryDao.getAllCategories()
    val account = accountDao.getAllAccounts()

    suspend fun insertTransaction(transaction: Transaction){
        transactionDao.insertTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction){
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun insertCategory(category: Category){
        categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category){
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category){
        categoryDao.deleteCategory(category)
    }

    suspend fun insertAccount(account: Account){
        accountDao.insertAccount(account)
    }

    suspend fun deleteAccount(account: Account){
        accountDao.deleteAccount(account)
    }

}