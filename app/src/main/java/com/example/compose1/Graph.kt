package com.example.compose1

import android.content.Context
import com.example.compose1.db.AppDatabase
import com.example.compose1.db.repository.Repository

object Graph {
    lateinit var db:AppDatabase
        private set
    val repository by lazy {
        Repository(
            transactionDao = db.TransactionDao(),
            categoryDao = db.CategoryDao(),
            accountDao = db.AccountDao()
        )
    }
    fun provide(context: Context){
        db = AppDatabase.getDatabase(context)
    }
}